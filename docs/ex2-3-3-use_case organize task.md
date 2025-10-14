# Use Case: Assign task to Category

### Name: 
Provide a descriptive name in the form of **Verb + Object**.

organize task

### Summary: 
Short descriptive summary 2-4 sentences.

a user created task is put in a category by the user himself. category may for example be "exam preparation" or "mathematics"

### Actor:
The actor initiating the use case.

    User

### Triggering Event:
Describing the event that starts the use case.

    User selects a categorie for an existing task

### Inputs:
Describing the information, inputs the system requires from outside.

    name of the category
    available categories

### Pre-Conditions:
Describing the preconditions that must be met to trigger the use case.

    user is logged in
    task is created

### Process Description:
Describing the normal process flow of the use case.

    user opens task
    user selects category for this specific task

### Exceptions:
Describing exceptions to the normal process.
    
    Category name invalid
    
### Extensions:

    category does not exist and must be created
    
### Outputs and Post-Conditions:
The result of the process and the conditions that must be met after the process has been executed.

    task is categorized
    user can see the category when viewing the task

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

# Use Case: Export Tasks

### Name: 
Provide a descriptive name in the form of **Verb + Object**.

export tasks

### Summary: 
Short descriptive summary 2-4 sentences.

a user exports selected tasks as a PDF summary or calendar file (ICS) to share or back up their data.

### Actor:
The actor initiating the use case.

    User (Student or Group Admin)

### Triggering Event:
Describing the event that starts the use case.

    user selects "export tasks" from the menu

### Inputs:
Describing the information, inputs the system requires from outside.

    selected tasks or date range
    chosen export format (PDF or ICS)

### Pre-Conditions:
Describing the preconditions that must be met to trigger the use case.

    user is logged in
    at least one task exists

### Process Description:
Describing the normal process flow of the use case.

    user opens export menu
    user selects tasks and desired format
    system sends export request to export service
    export service generates file
    system provides download link to user

### Exceptions:
Describing exceptions to the normal process.

    export service not reachable
    no tasks selected

### Extensions:

    user can choose to send export file via email

### Outputs and Post-Conditions:
The result of the process and the conditions that must be met after the process has been executed.

    export file is generated and ready for download
    confirmation message displayed to user
