Feature: UC01 Create Personal Task
  Student is creating a personal task to track study work efficiently.

  Background:
    Given a Student is authenticated
    And the Student is on the My Tasks page

  Scenario: Create a task with required fields
    When the Student opens the Add Task form
    And the Student enters a title "Read Chapter 3"
    And the Student selects a due date "2025-11-05 14:00"
    And the Student selects priority "Medium"
    And the Student optionally enters notes "Focus on sections 3.2 and 3.3"
    And the Student saves the task
    Then the task is created with status "Open"
    And the task appears in the list view
    And the task appears in the calendar view on "2025-11-05"

  Scenario Outline: Validation of title and notes length
    When the Student opens the Add Task form
    And the Student enters a title <title>
    And the Student selects a due date "2025-11-05"
    And the Student selects priority "Low"
    And the Student optionally enters notes <notes>
    And the Student saves the task
    Then the form shows validation <validation>

    Examples:
      | title       | notes      | validation                   |
      | ""          | "ok"       | "Title is required"          |
      | "A...201"   | "ok"       | "Title must be <= 200 chars" |
      | "Valid"     | "N...1001" | "Notes must be <= 1000 chars"|
      | "Valid"     | "Short"    | "Success"                    |

  Scenario: Cancel task creation
    When the Student opens the Add Task form
    And the Student enters a title "Temp"
    And the Student cancels the form
    Then no new task is created

  Scenario: Retry after transient API failure
    When the Student opens the Add Task form
    And the Student completes the form with valid values
    And the first save attempt fails due to a network error
    And the Student retries the save
    Then the task is created successfully

