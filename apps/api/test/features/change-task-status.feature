Feature: Change Task Status
  As a student
  I want to update a task’s status
  So that I can track progress (open, in progress, completed, overdue)

  Background:
    Given an authenticated user
    And an existing task "Read Chapter 5" with status "open"

  @happy
  Scenario: Move task from OPEN → IN_PROGRESS → COMPLETED
    Given an existing task "Read Chapter 5" with status "OPEN"
    When the user changes status of "Read Chapter 5" to "IN_PROGRESS"
    And the user changes status of "Read Chapter 5" to "COMPLETED"
    Then the task "Read Chapter 5" shows status "COMPLETED"

  @invalid-transition
  Scenario: Reject invalid transition (COMPLETED → OPEN)
    Given the task "Read Chapter 5" has status "COMPLETED"
    When the user changes status of "Read Chapter 5" to "OPEN"
    Then the user sees a warning "Invalid status transition"
    And the status remains "COMPLETED"

  @clocked @overdue
  Scenario: Automatically mark as overdue when dueDate < now and not COMPLETED
    Given the task "Midterm prep" with status "IN_PROGRESS" and dueDate "2030-05-10T12:00:00Z"
    And the test clock is set to "2030-05-11T12:00:00Z"
    When the system evaluates overdue tasks
    Then the task "Midterm prep" is marked as "OVERDUE"