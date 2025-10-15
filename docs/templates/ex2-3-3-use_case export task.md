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
