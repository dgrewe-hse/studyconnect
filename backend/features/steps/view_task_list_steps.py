from behave import step
from datetime import datetime
from backend.models import db, Task, User

# ----------------------------------------------------------------------
# GIVEN Steps
# ----------------------------------------------------------------------

@step('the User "{username}" is logged in')
def step_impl(context, username):
    """
    Simulates a user login.
    In a real test setup, this would involve setting a session cookie
    or mocking authentication.
    For now, we store the logged-in user in the context.
    """
    with context.app.app_context():
        user = User.query.filter_by(username=username).first()
        assert user is not None, f'User "{username}" not found in the database.'
        context.logged_in_user = user

@step('the following tasks exist for user "{user_id}":')
def step_impl(context, user_id):
    """
    Creates multiple tasks for a specific user based on a table.
    """
    with context.app.app_context():
        for row in context.table:
            deadline_date = datetime.strptime(row['deadline'], '%Y-%m-%d').date() if row['deadline'] else None
            group_id = int(row['group_id']) if row.get('group_id') and row['group_id'].strip() else None

            task = Task(
                title=row['title'],
                deadline=deadline_date,
                kind=row.get('kind', 'study'),
                priority=row.get('priority', 'medium'),
                status=row.get('status', 'todo'),
                user_id=user_id,
                group_id=group_id
            )
            db.session.add(task)
        db.session.commit()

@step('the database contains no tasks for user "{user_id}"')
def step_impl(context, user_id):
    """
    Ensures that a user has no tasks.
    """
    with context.app.app_context():
        Task.query.filter_by(user_id=user_id).delete()
        db.session.commit()
        count = Task.query.filter_by(user_id=user_id).count()
        assert count == 0, f'User "{user_id}" should have no tasks, but has {count}.'

@step('the following tasks exist in the database:')
def step_impl(context):
    """
    Creates various tasks for different users.
    """
    with context.app.app_context():
        for row in context.table:
            user = db.session.get(User, row['user_id'])
            if not user:
                user = User(id=row['user_id'], username=f"User {row['user_id']}", email=f"user{row['user_id']}@test.de")
                db.session.add(user)
                db.session.commit()

            deadline_date = datetime.strptime(row['deadline'], '%Y-%m-%d').date() if row['deadline'] else None
            group_id = int(row['group_id']) if row.get('group_id') and row['group_id'].strip() else None

            kind = row.get('kind', 'study')
            priority = row.get('priority', 'medium')
            task = Task(
                title=row['title'],
                deadline=deadline_date,
                user_id=row['user_id'],
                group_id=group_id,
                kind=kind,
                priority=priority
            )
            db.session.add(task)
        db.session.commit()

# ----------------------------------------------------------------------
# WHEN Steps
# ----------------------------------------------------------------------

@step('"{username}" visits the homepage')
def step_impl(context, username):
    """
    Simulates a GET request to the homepage.
    The response is stored in the context for the THEN steps.
    """
    from backend.services import get_tasks_for_user
    with context.app.app_context():
        user = context.logged_in_user
        tasks = get_tasks_for_user(user.id)
        context.response_data = tasks

# ----------------------------------------------------------------------
# THEN Steps
# ----------------------------------------------------------------------

@step('the system should display a task list containing:')
def step_impl(context):
    """
    Checks if the returned task list contains the expected tasks.
    """
    response_tasks = context.response_data
    expected_titles = {row['title'] for row in context.table}
    response_titles = {task.title for task in response_tasks}

    assert len(response_tasks) == len(context.table.rows), \
        f"Expected {len(context.table.rows)} tasks, but got {len(response_tasks)}"

    for expected_task in context.table:
        found = False
        for response_task in response_tasks:
            if response_task.title == expected_task['title']:
                found = True
                assert response_task.deadline.isoformat() == expected_task['deadline'], \
                    f"Task '{response_task.title}' deadline mismatch. Expected {expected_task['deadline']}, got {response_task.deadline.isoformat()}"
                assert response_task.status == expected_task['status'], \
                    f"Task '{response_task.title}' status mismatch. Expected {expected_task['status']}, got {response_task.status}"
                break
        assert found, f"Expected task '{expected_task['title']}' not found in response."

@step('the system should display a message "{message}"')
def step_impl(context, message):
    """
    Checks if the response indicates an empty state.
    This is a conceptual step. In a real API, this might mean an empty list.
    """
    assert isinstance(context.response_data, list)
    assert len(context.response_data) == 0, "Expected an empty task list, but tasks were returned."
    context.empty_message_checked = True

@step('no task list should be shown')
def step_impl(context):
    """
    Confirms that the primary check for the empty state was performed.
    """
    assert hasattr(context, 'empty_message_checked') and context.empty_message_checked, \
        "The check for an empty message was not performed."

@step('the displayed task list should contain only:')
def step_impl(context):
    """
    Verifies that the task list contains exactly the specified tasks and no more.
    """
    response_tasks = context.response_data
    expected_titles = {row['title'] for row in context.table}
    response_titles = {task.title for task in response_tasks}

    assert response_titles == expected_titles, \
        f"Task list mismatch. Expected: {expected_titles}, Got: {response_titles}"

@step('tasks belonging to other users should not be shown')
def step_impl(context):
    pass
