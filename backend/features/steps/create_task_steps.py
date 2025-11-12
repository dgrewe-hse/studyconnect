from behave import step
from datetime import datetime, date
from backend.models import db, User, Group, Task
from backend.services import create_task_service
from sqlalchemy import func
from backend import services 

# Helper to parse Gherkin tables into a dictionary.
def table_to_dict(table):
    data = {}
    for row in table.rows:
        data[row['Field']] = row['Value']
    return data

# ----------------------------------------------------------------------
# GIVEN Steps: Setup the context for each scenario.
# ----------------------------------------------------------------------

@step('a User "{username}" with ID "{user_id}" exists')
def step_impl(context, username, user_id):
    """Initializes a user for the context."""
    with context.app.app_context(): 
        user = User(id=user_id, username=username, email=f"{username.replace(' ', '.').lower()}@test.de")
        db.session.add(user)
        db.session.commit()
        context.user = user

@step('a Group "{group_name}" with ID {group_id:d} exists')
def step_impl(context, group_name, group_id):
    """Initializes a group for the context."""
    with context.app.app_context(): 
        group = db.session.get(Group, group_id)
        if not group:
            group = Group(id=group_id, name=group_name, group_number=str(group_id), invite_link=f"link{group_id}")
            db.session.add(group)
            db.session.commit()
        context.group = group

@step('the database contains no task with the title "{title}"')
def step_impl(context, title):
    """Ensures that no task with this title exists."""
    with context.app.app_context(): 
        assert db.session.query(Task).filter_by(title=title).first() is None

@step('today\'s date is {date_str}')
def step_impl(context, date_str):
    """Sets the 'today' date for tests that use the current date."""
    context.today_date = datetime.strptime(date_str, '%Y-%m-%d').date()
    
    class FakeDate(date):
        @classmethod
        def today(cls):
            return context.today_date
    context.mock_date = FakeDate

@step('an existing task is present with the following details:')
def step_impl(context):
    """Creates a duplicate task that should be found later."""
    data = table_to_dict(context.table)
    data['kind'] = data.get('kind', 'study')
    data['priority'] = data.get('priority', 'low')

    deadline_date = datetime.strptime(data['deadline'], '%Y-%m-%d').date()
    with context.app.app_context(): 
        existing_task = Task(
            title=data['title'],
            deadline=deadline_date,
            user_id=data['user_id'],
            group_id=int(data['group_id']) if 'group_id' in data else None,
            kind=data['kind'],
            priority=data['priority']
        )
        db.session.add(existing_task)
        db.session.commit()
        context.existing_task = existing_task
        context.existing_task_id = existing_task.id

# ----------------------------------------------------------------------
# WHEN Steps: Trigger the action to be tested.
# ----------------------------------------------------------------------

@step('"{username}" creates a new task with the following details:')
@step('"{username}" attempts to create a new task with the following details:')
@step('"{username}" attempts to create a new task with the exact same details:')
def step_impl(context, username):
    """Calls the create_task_service, catches exceptions, and sets a mock date."""
    data = table_to_dict(context.table)
    original_date = None
    if hasattr(context, 'mock_date'):
        original_date = services.date
        services.date = context.mock_date 

    with context.app.app_context(): 
        try:
            task_object = create_task_service(data)
            context.task = task_object
            context.last_task_id = task_object.id
        except Exception as e:
            context.exception = e
    
    if original_date:
        services.date = original_date

# ----------------------------------------------------------------------
# THEN Steps: Verify the outcome of the action.
# ----------------------------------------------------------------------

@step('a new task with the title "{title}" should have been created')
def step_impl(context, title):
    """Checks for successful creation in the database."""
    assert context.exception is None, f"Es wurde eine unerwartete Exception ausgelöst: {context.exception}"

    with context.app.app_context(): 
        assert context.task is not None
        task_in_db = db.session.get(Task, context.last_task_id)
        
        assert task_in_db is not None
        assert task_in_db.title == title

@step('the task status should be "{status}"')
def step_impl(context, status):
    """Checks the status of the last created/returned task."""

    with context.app.app_context(): 
        task_in_db = db.session.get(Task, context.last_task_id)
        assert task_in_db.status == status

@step('the task creator should be "{user_id}"')
def step_impl(context, user_id):
    """Checks the creator (user_id) of the task."""
    with context.app.app_context(): 
        task_in_db = db.session.get(Task, context.last_task_id)
        assert task_in_db.user_id == user_id

@step('the creation should fail')
def step_impl(context):
    """Checks if an exception was raised."""
    assert context.exception is not None, "Die Erstellung sollte fehlschlagen, es wurde aber keine Exception gefunden."

@step('an error message containing "{message}" should be displayed')
def step_impl(context, message):
    """Checks the content of the raised exception message."""
    assert message in str(context.exception)
    
@step('no new task should be created')
def step_impl(context):
    """Checks that the number of tasks is equal to the original number (1)."""
    with context.app.app_context(): 
        assert db.session.query(func.count(Task.id)).scalar() == 1
    
@step('the existing task should be returned')
def step_impl(context):
    """Checks if the result is the already existing task."""
    with context.app.app_context():
        assert context.task.id == context.existing_task_id