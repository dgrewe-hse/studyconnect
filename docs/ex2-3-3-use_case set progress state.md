# Use Case: set progress state of task

### Name: 
Provide a descriptive name in the form of **Verb + Object**.

    set progress state

### Summary: 
Short descriptive summary 2-4 sentences.

  An existing task can be put in three different progress states, depending on how far away from fulfilment the tasks are.
  The three states are "open", "in progress" and "completed"

### Actor:
The actor initiating the use case.

    User

### Triggering Event:
Describing the event that starts the use case.
  
  User selects a task
  User selects a progress state for the specific task

### Inputs:
Describing the information, inputs the system requires from outside.

  task-#
  present progress state of the task  
  future progress state of the task

### Pre-Conditions:
Describing the preconditions that must be met to trigger the use case.

  user must be logged in
  user must select task

### Process Description:
Describing the normal process flow of the use case.

    user logs in
    user selects task
    user selects progress state
    user saves the new task progress

### Exceptions:
Describing exceptions to the normal process.

    <Text goes here>

### Extensions:
    
    user aborts the process before saving new progress state
      present state is not altered

    database failure
      user sees error message and may try again


### Outputs and Post-Conditions:
The result of the process and the conditions that must be met after the process has been executed.

    progress state is updated in the database    
    user sees task with the new progress state
    
