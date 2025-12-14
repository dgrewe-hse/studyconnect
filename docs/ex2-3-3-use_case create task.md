# Use Case: Create Task

### Name: 
Provide a descriptive name in the form of **Verb + Object**.

create task

### Summary: 
Short descriptive summary 2-4 sentences.

a user creates a new task to manage study-related activities such as assignments or projects. the task may include information like title, description, due date, and priority.

### Actor:
The actor initiating the use case.

    User

### Triggering Event:
Describing the event that starts the use case.

    user selects "create new task" in the application

### Inputs:
Describing the information, inputs the system requires from outside.

    task title
    description
    due date
    priority

### Pre-Conditions:
Describing the preconditions that must be met to trigger the use case.

    user is logged in

### Process Description:
Describing the normal process flow of the use case.

    user opens the task section
    user clicks "create new task"
    system displays input form
    user enters task details
    system validates inputs and saves task
    task appears in the user's list

### Exceptions:
Describing exceptions to the normal process.

    required fields missing
    connection or database error

### Extensions:

    user can directly assign task to a group or category during creation

### Outputs and Post-Conditions:
The result of the process and the conditions that must be met after the process has been executed.

    new task is stored in the system
    confirmation message is displayed
    task is visible in the user’s task list
