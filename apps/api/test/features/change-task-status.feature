Feature: Change Task Status
  As a student
  I want to update a task’s status
  So that I can track progress (open, in progress, completed, overdue)

  Background:
    Given an authenticated user
    And an existing task "Read Chapter 5" with status "open"

  @happy
  Scenario: Move task from open → in progress → completed
    When the user changes status of "Read Chapter 5" to "in progress"
    And the user changes status of "Read Chapter 5" to "completed"
    Then the task "Read Chapter 5" shows status "completed"

  @invalid-transition
  Scenario: Reject invalid transition (completed → open)
    Given the task "Read Chapter 5" has status "completed"
    When the user changes status of "Read Chapter 5" to "open"
    Then the user sees a warning "Invalid status transition"
    And the status remains "completed"

  @clocked @overdue
  Scenario: Automatically mark as overdue when dueAt < now and not completed
    Given the task "Midterm prep" with status "in progress" and dueAt "2030-05-10T12:00:00Z"
    And the test clock is set to "2030-05-11T12:00:00Z"
    When the system evaluates overdue tasks
    Then the task "Midterm prep" is marked as "overdue"
