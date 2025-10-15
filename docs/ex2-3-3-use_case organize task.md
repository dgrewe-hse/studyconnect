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

### Outputs and Post-Conditions:
The result of the process and the conditions that must be met after the process has been executed.

    export file is generated and ready for download
    confirmation message displayed to user
