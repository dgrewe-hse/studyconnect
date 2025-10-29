Feature: UC02 Edit and Complete Personal Task
  Student is updating a personal task and marking it complete to reflect progress.

  Background:
    Given a Student is authenticated
    And at least one task exists titled "Read Chapter 3" with status "Open"

  Scenario: Edit task details and save
    When the Student opens the task "Read Chapter 3"
    And the Student changes the due date to "2025-11-06"
    And the Student changes priority to "High"
    And the Student updates notes to "Add summary notes"
    And the Student saves changes
    Then the task shows due date "2025-11-06" and priority "High"
    And the notes show "Add summary notes"

  Scenario: Mark task as completed
    When the Student opens the task "Read Chapter 3"
    And the Student marks the task as "Completed"
    Then the task status is "Completed" with a completion indicator
    And the task is still visible in the list with completed styling

  Scenario: Reopen a completed task
    Given the task "Read Chapter 3" is "Completed"
    When the Student marks the task as "Open"
    Then the task status is "Open"

  Scenario Outline: Validation of edits (required title, optional past due date)
    When the Student opens the task "Read Chapter 3"
    And the Student sets the title to <title>
    And the Student sets the due date to <dueDate>
    And the Student saves changes
    Then the form shows validation <validation>

    Examples:
      | title         | dueDate       | validation                |
      | ""            | "2025-11-06" | "Title is required"      |
      | "Keep Title"  | "2024-10-01" | "Warning allowed; saved" |
      | "Keep Title"  | "2025-11-08" | "Success"                |

