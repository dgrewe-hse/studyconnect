Feature: Create Task
  As a logged-in user or a team member
  I want to create a new task
  In order to organize my academic assignments or team tasks.

  Background:
    Given a User "Max Mustermann" with ID "user123" exists
    And a Group "Databases" with ID 1 exists



  # ----------------------------------------------------
  # Scenario 1: Successful creation of a personal task
  # ----------------------------------------------------
  Scenario: Successfully create a personal task
    Given the database contains no task with the title "Practice SQL Queries"
    When "Max Mustermann" creates a new task with the following details:
      | Field | Value |
      | title | Practice SQL Queries |
      | deadline | 2026-12-15 |
      | kind | study |
      | priority | high |
      | user_id | user123 |
    Then a new task with the title "Practice SQL Queries" should have been created
    And the task status should be "todo"
    And the task creator should be "user123"
    


  # ----------------------------------------------------
  # Scenario 2: Attempting to create a task with an expired deadline
  # (Testing the validation: deadline_date < date.today())
  # ----------------------------------------------------
  Scenario: Failure when creating a task with a past deadline
    Given today's date is 2025-11-03
    When "Max Mustermann" attempts to create a new task with the following details:
      | Field | Value |
      | title | Historical Assignment |
      | deadline | 2025-01-01 |
      | kind | project |
      | priority | low |
      | user_id | user123 |
    Then the creation should fail
    And an error message containing "Deadline cannot be in the past" should be displayed



  # ----------------------------------------------------
  # Scenario 3: Attempting to create a duplicate team task
  # (Testing the duplicate check: check for existing duplicate task)
  # ---------------------------------------------------- Notiz Erik: am ende checken ob es noch klappt
  Scenario: Do not re-create an existing task
    Given an existing task is present with the following details:
      | Field    | Value                            |
      | title    | Write Documentation for Sprint 1 |
      | deadline | 2026-11-30                       |
      | user_id  | user123                          |
      | group_id | 1                                |
    When "Max Mustermann" attempts to create a new task with the exact same details:
      | Field    | Value                            |
      | title    | Write Documentation for Sprint 1 |
      | deadline | 2026-11-30 |
      | kind | project |
      | priority | medium |
      | user_id | user123 |
      | group_id | 1 |
    Then no new task should be created
    And the existing task should be returned