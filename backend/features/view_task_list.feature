Feature: View Task List
  As a logged-in user
  I want to view a list of my tasks
  In order to have an overview of all my academic or team-related assignments.

  Background:
    Given a User "Max Mustermann" with ID "user123" exists
    And the User "Max Mustermann" is logged in



  # ----------------------------------------------------
  # Scenario 1: Viewing task list with existing tasks
  # ----------------------------------------------------
  Scenario: Successfully view all tasks assigned to the user
    Given the following tasks exist for user "user123":
      | title                         	 | deadline   | kind    | priority | status 	 | group_id |
      | Practice SQL Queries          	 | 2026-12-15 | study   | high     | todo   	 |          |
      | Write Documentation for Sprint 1 | 2026-11-30 | project | medium   | in_progress | 		  1 |
    When "Max Mustermann" visits the homepage
    Then the system should display a task list containing:
      | title                         	 | deadline   | status       |
      | Practice SQL Queries           	 | 2026-12-15 | todo         |
      | Write Documentation for Sprint 1 | 2026-11-30 | in_progress  |



  # ----------------------------------------------------
  # Scenario 2: Viewing task list when no tasks exist
  # ----------------------------------------------------
  Scenario: Display empty state when user has no tasks
    Given the database contains no tasks for user "user123"
    When "Max Mustermann" visits the homepage
    Then the system should display a message "No tasks available"
    And no task list should be shown



  # ----------------------------------------------------
  # Scenario 3: Viewing only the tasks of the logged-in user
  # ----------------------------------------------------
  Scenario: Ensure user only sees their own tasks
    Given the following tasks exist in the database:
      | title                  | deadline   | user_id  | group_id |
      | Private Task           | 2026-10-10 | user123  |          |
      | Team Task for Anna     | 2026-12-01 | user456  | 2        |
    When "Max Mustermann" visits the homepage
    Then the displayed task list should contain only:
      | title        | deadline   |
      | Private Task | 2026-10-10 |
    And tasks belonging to other users should not be shown
    