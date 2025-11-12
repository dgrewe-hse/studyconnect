from behave import step
from datetime import datetime
from backend.models import db, User, Group, Task


# Utility: ensure notifications storage on context
def _ensure_notifications_context(context):
    if not hasattr(context, 'notifications'):
        context.notifications = []  # list of dicts: {user_id, message, type, status, high_priority?, task_id?}
    if not hasattr(context, 'task_deadlines_by_task_id'):
        context.task_deadlines_by_task_id = {}  # Task.id -> datetime with time
    if not hasattr(context, 'tasks_by_title'):
        context.tasks_by_title = {}


# Utility: parse a table with arbitrary headers into list of dicts
def _table_to_dicts(table):
    return [dict(row.items()) for row in table]


# ----------------------------------------------------------------------
# GIVEN steps
# ----------------------------------------------------------------------

@step('"{user1}" and "{user2}" are members of Group "{group_name}"')
def step_impl(context, user1, user2, group_name):
    _ensure_notifications_context(context)
    with context.app.app_context():
        group = Group.query.filter_by(name=group_name).first()
        assert group is not None, f'Group "{group_name}" not found.'

        u1 = User.query.filter_by(username=user1).first()
        u2 = User.query.filter_by(username=user2).first()
        assert u1 is not None, f'User "{user1}" not found.'
        assert u2 is not None, f'User "{user2}" not found.'

        if group not in u1.groups:
            u1.groups.append(group)
        if group not in u2.groups:
            u2.groups.append(group)
        db.session.commit()


@step('"{username}" is logged in')
def step_impl(context, username):
    _ensure_notifications_context(context)
    with context.app.app_context():
        user = User.query.filter_by(username=username).first()
        assert user is not None, f'User "{username}" not found.'
        context.logged_in_user = user


@step('today\'s date and time is "{iso_dt}"')
def step_impl(context, iso_dt):
    """Sets a mocked current datetime for notifications logic."""
    _ensure_notifications_context(context)
    context.current_time = datetime.strptime(iso_dt, '%Y-%m-%d %H:%M')


@step('the following task exists for user "{user_id}":')
def step_impl(context, user_id):
    """Create a single task for a user, storing the full datetime deadline for notifications."""
    _ensure_notifications_context(context)
    rows = _table_to_dicts(context.table)
    assert len(rows) == 1, 'Expected exactly one task row.'
    data = rows[0]

    title = data['title']
    deadline_full = datetime.strptime(data['deadline'], '%Y-%m-%d %H:%M')
    status = data.get('status', 'todo')

    with context.app.app_context():
        task = Task(
            title=title,
            deadline=deadline_full.date(),  # persist date only as per model
            kind='study',
            priority='medium',
            status=status,
            user_id=user_id,
            group_id=None
        )
        db.session.add(task)
        db.session.commit()

        context.task_deadlines_by_task_id[task.id] = deadline_full
        context.tasks_by_title[title] = task
        context.last_task_title = title
        context.last_task_id = task.id


@step('"{username}" has no unread notifications')
def step_impl(context, username):
    _ensure_notifications_context(context)
    with context.app.app_context():
        user = User.query.filter_by(username=username).first()
        assert user is not None, f'User "{username}" not found.'
        # Remove or mark all unread notifications as read for this user
        for n in context.notifications:
            if n['user_id'] == user.id and n.get('status', 'unread') == 'unread':
                n['status'] = 'read'
        # Assert no unread remain
        assert all(n.get('status') != 'unread' for n in context.notifications if n['user_id'] == user.id), \
            f'User {username} should have no unread notifications.'


@step('the following unread notifications exist for "{user_id}":')
def step_impl(context, user_id):
    _ensure_notifications_context(context)
    for row in context.table:
        msg = row['message'].strip().strip('"')
        typ = row['type']
        context.notifications.append({
            'user_id': user_id,
            'message': msg,
            'type': typ,
            'status': 'unread',
        })


# ----------------------------------------------------------------------
# WHEN steps
# ----------------------------------------------------------------------

@step('the system checks for pending notifications for "{user_id}"')
def step_impl(context, user_id):
    _ensure_notifications_context(context)
    now = getattr(context, 'current_time', datetime.now())
    new_notifications = []

    with context.app.app_context():
        tasks = Task.query.filter_by(user_id=user_id).all()
        for task in tasks:
            deadline_dt = context.task_deadlines_by_task_id.get(task.id)
            # If no precise time was stored, default to 00:00 of the task's date
            if not deadline_dt:
                deadline_dt = datetime.combine(task.deadline, datetime.min.time())

            # Only consider tasks not done
            if task.status != 'todo':
                continue

            delta = deadline_dt - now
            if delta.total_seconds() <= 0:
                # overdue
                note = {
                    'user_id': user_id,
                    'message': f"Your task '{task.title}' is overdue ({deadline_dt.strftime('%Y-%m-%d %H:%M')}).",
                    'type': 'overdue',
                    'status': 'unread',
                    'high_priority': True,
                    'task_id': task.id,
                }
                context.notifications.append(note)
                new_notifications.append(note)
            elif delta.total_seconds() <= 24 * 3600:
                # due soon
                note = {
                    'user_id': user_id,
                    'message': f"Your task '{task.title}' is due soon ({deadline_dt.strftime('%Y-%m-%d %H:%M')}).",
                    'type': 'due_soon',
                    'status': 'unread',
                    'task_id': task.id,
                }
                context.notifications.append(note)
                new_notifications.append(note)

    context.new_notifications = new_notifications


@step('"{assigner_name}" creates a new task in Group "{group_name}" and assigns it to "{assignee_id}":')
def step_impl(context, assigner_name, group_name, assignee_id):
    _ensure_notifications_context(context)
    rows = _table_to_dicts(context.table)
    assert len(rows) == 1, 'Expected exactly one task row.'
    data = rows[0]

    title = data['title']
    deadline_full = datetime.strptime(data['deadline'], '%Y-%m-%d %H:%M')
    group_id = int(data['group_id']) if data.get('group_id') else None

    with context.app.app_context():
        assigner = User.query.filter_by(username=assigner_name).first()
        assert assigner is not None, f'Assigner "{assigner_name}" not found.'
        group = Group.query.filter_by(name=group_name).first()
        if not group and group_id:
            group = db.session.get(Group, group_id)
        assert group is not None, f'Group "{group_name}" not found.'

        task = Task(
            title=title,
            deadline=deadline_full.date(),
            kind='study',
            priority='medium',
            status='todo',
            user_id=assignee_id,
            group_id=group.id
        )
        db.session.add(task)
        db.session.commit()

        context.task_deadlines_by_task_id[task.id] = deadline_full
        context.tasks_by_title[title] = task
        context.last_task_title = title
        context.last_task_id = task.id

        # Generate assignment notification
        note = {
            'user_id': assignee_id,
            'message': f"{assigner_name} assigned you to a new task: '{title}'.",
            'type': 'new_assignment',
            'status': 'unread',
            'task_id': task.id,
        }
        context.notifications.append(note)
        context.new_notifications = [note]


@step('"{username}" views their notification list')
def step_impl(context, username):
    _ensure_notifications_context(context)
    with context.app.app_context():
        user = User.query.filter_by(username=username).first()
        assert user is not None, f'User "{username}" not found.'
        # Simulate viewing: fetch unread for this user, then mark them read
        to_view = [n for n in context.notifications if n['user_id'] == user.id and n.get('status', 'unread') == 'unread']
        for n in to_view:
            n['status'] = 'read'
        context.viewed_notifications = to_view


# ----------------------------------------------------------------------
# THEN steps
# ----------------------------------------------------------------------

@step('a new notification should be generated')
def step_impl(context):
    assert hasattr(context, 'new_notifications'), 'No notification check was performed.'
    assert len(context.new_notifications) >= 1, 'Expected at least one new notification.'


@step('a new notification should be generated for "{user_id}"')
def step_impl(context, user_id):
    assert hasattr(context, 'new_notifications'), 'No notification check was performed.'
    assert any(n['user_id'] == user_id for n in context.new_notifications), \
        f'No new notification for user {user_id}.'


@step('the notification message should be "{message}"')
def step_impl(context, message):
    assert any(n['message'] == message for n in context.new_notifications), \
        f'Expected message not found. Got: {[n["message"] for n in context.new_notifications]}'


@step('the notification type should be "{notif_type}"')
def step_impl(context, notif_type):
    assert any(n['type'] == notif_type for n in context.new_notifications), \
        f'Expected type {notif_type} not found.'


@step('the notification status should be "{status}"')
def step_impl(context, status):
    assert any(n.get('status') == status for n in context.new_notifications), \
        f'Expected status {status} not found.'


@step('the notification should be marked as "high_priority"')
def step_impl(context):
    assert any(n.get('high_priority') for n in context.new_notifications), \
        'Expected a high_priority notification.'


@step('no "{t1}" or "{t2}" notification should be generated for this task')
def step_impl(context, t1, t2):
    title = getattr(context, 'last_task_title', None)
    assert title is not None, 'No reference task title stored.'
    assert all(n['type'] not in (t1, t2) for n in context.new_notifications if n.get('task_id') == context.last_task_id), \
        f'Unexpected {t1}/{t2} notification generated for task {title}.'


@step('the list should display {count:d} notifications')
def step_impl(context, count):
    assert hasattr(context, 'viewed_notifications'), 'Notifications have not been viewed.'
    assert len(context.viewed_notifications) == count, \
        f'Expected {count} notifications, got {len(context.viewed_notifications)}.'


@step('the notification "{message}" should be visible')
def step_impl(context, message):
    assert any(n['message'] == message for n in context.viewed_notifications), \
        f'Notification message not visible: {message}'


@step('all displayed notifications should be marked as "read"')
def step_impl(context):
    assert all(n.get('status') == 'read' for n in context.viewed_notifications), \
        'Not all displayed notifications are marked as read.'
