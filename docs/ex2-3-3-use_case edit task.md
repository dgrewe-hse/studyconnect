# Use Case: Edit Task

### Name: 
Provide a descriptive name in the form of **Verb + Object**.

edit task

### Summary: 
Short descriptive summary 2-4 sentences.

a user modifies details of an existing task, such as deadline, description, or progress, to keep information up to date.

### Actor:
The actor initiating the use case.

    User (Task Owner or Group Admin)

### Triggering Event:
Describing the event that starts the use case.

    user selects "edit task" on an existing task

### Inputs:
Describing the information, inputs the system requires from outside.

    updated task information (title, description, due date, etc.)

### Pre-Conditions:
Describing the preconditions that must be met to trigger the use case.

    user is logged in
    task exists in the system
    user has permission to edit

### Process Description:
Describing the normal process flow of the use case.

    user opens the existing task
    user selects "edit"
    system displays editable fields
    user changes the task details
    system validates and saves changes
    system displays updated task information

### Exceptions:
Describing exceptions to the normal process.

    user lacks edit permissions
    invalid date format
    connection or save error

### Extensions:

    user can attach files or add comments during edit

### Outputs and Post-Conditions:
The result of the process and the conditions that must be met after the process has been executed.

    task details are updated and saved
    updated task is visible to all authorized users
