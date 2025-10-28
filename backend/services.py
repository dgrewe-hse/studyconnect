from datetime import datetime, date
from sqlalchemy import and_
from models import db, User, Group, Task

VALID_PRIORITIES = ['low', 'medium', 'high']
VALID_STATUSES = {
    'todo': ['in_progress'],
    'in_progress': ['done', 'blocked'],
    'blocked': ['in_progress'],
    'done': []
}

# -----------------------------
# User Services
# -----------------------------
def get_or_create_user_from_keycloak(keycloak_userinfo):
    """Ensure a Keycloak user exists in local DB."""
    user_id = keycloak_userinfo.get("sub")
    username = keycloak_userinfo.get("preferred_username") or keycloak_userinfo.get("email")
    email = keycloak_userinfo.get("email")

    if not user_id:
        raise Exception("Missing Keycloak user ID (sub)")

    user = User.query.get(user_id)
    if not user:
        user = User(id=user_id, username=username, email=email)
        db.session.add(user)
        db.session.commit()
    return user


def get_user_service(user_id: str):
    """Get a user by ID or raise."""
    user = User.query.get(user_id)
    if not user:
        raise Exception(f"User with id {user_id} does not exist")
    return user


# -----------------------------
# Task Services
# -----------------------------
def create_task_service(data):
    # Validate deadline
    deadline_date = datetime.strptime(data['deadline'], '%Y-%m-%d').date()
    if deadline_date < date.today():
        raise ValueError("Deadline cannot be in the past")

    user_id = data.get('user_id')  # string
    group_id = data.get('group_id')

    # check for existing duplicate task
    existing_task = Task.query.filter(
        and_(
            Task.title == data['title'],
            Task.deadline == deadline_date,
            Task.user_id == user_id,
            Task.group_id == group_id
        )
    ).first()
    if existing_task:
        return existing_task

    task = Task(
        title=data['title'],
        deadline=deadline_date,
        kind=data['kind'],
        priority=data['priority'],
        status=data.get('status', 'todo'),
        user_id=user_id,
        group_id=group_id,
        assignee=data.get('assignee'),
        notes=data.get('notes'),
        progress=data.get('progress', 0)
    )
    db.session.add(task)
    db.session.commit()
    return task


def update_task_service(task_id, data):
    task = Task.query.get(task_id)
    if not task:
        raise Exception(f"Task with id {task_id} does not exist")

    # Validate status transition
    if 'status' in data:
        current_status = task.status
        new_status = data['status']
        if new_status not in VALID_STATUSES.get(current_status, []):
            raise ValueError(f"Invalid status transition from {current_status} to {new_status}")

    # Validate progress
    if 'progress' in data:
        progress = data['progress']
        if not (0 <= progress <= 100):
            raise ValueError("Progress must be between 0 and 100")

    # Validate priority
    if 'priority' in data:
        if data['priority'] not in VALID_PRIORITIES:
            raise ValueError(f"Invalid priority value. Must be one of: {VALID_PRIORITIES}")

    # Validate assignee
    if 'assignee' in data:
        assignee = User.query.get(data['assignee'])
        if not assignee:
            raise ValueError("Assignee user not found")
        if task.group_id and task.group_id not in [g.id for g in assignee.groups]:
            raise ValueError("Assignee must be member of the group")

    # Update fields
    for field in ['title', 'kind', 'priority', 'status', 'user_id', 'group_id', 'assignee', 'notes', 'progress']:
        if field in data:
            setattr(task, field, data[field])

    if 'deadline' in data:
        deadline_date = datetime.strptime(data['deadline'], '%Y-%m-%d').date()
        if deadline_date < date.today():
            raise ValueError("Deadline cannot be in the past")
        task.deadline = deadline_date

    db.session.commit()
    return task


def get_tasks_for_user(user_id: str):
    user = User.query.get(user_id)
    if not user:
        return []

    group_ids = [g.id for g in user.groups]
    return Task.query.filter(
        (Task.user_id == user_id) | (Task.group_id.in_(group_ids))
    ).all()


def get_all_tasks():
    return Task.query.all()


# -----------------------------
# Group Services
# -----------------------------
def create_group_service(data):
    existing_group = Group.query.filter(
        (Group.group_number == data['groupNumber']) |
        (Group.invite_link == data['inviteLink'])
    ).first()

    if existing_group:
        return existing_group

    group = Group(
        name=data['name'],
        description=data.get('description'),
        group_number=data['groupNumber'],
        invite_link=data['inviteLink']
    )
    db.session.add(group)
    db.session.commit()
    return group


def join_group_service(user_id: str, group_id: int):
    user = User.query.get(user_id)
    group = Group.query.get(group_id)

    if not user:
        raise Exception(f"User with id {user_id} does not exist")
    if not group:
        raise Exception(f"Group with id {group_id} does not exist")

    if group in user.groups:
        return group

    user.groups.append(group)
    db.session.commit()
    return group


def get_all_groups():
    return Group.query.all()


def get_groups_for_user(user_id: str):
    user = User.query.get(user_id)
    return user.groups if user else []
