Feature: Create Task
  As a student
  I want to create a task with title, optional deadline, category and priority
  So that I can manage my study activities efficiently

  Background:
    Given an authenticated user

  @happy
  Scenario: Create task with valid title, category, priority, and future deadline
    When the user creates a task with:
      | title      | "Read Chapter 5" |
      | category   | "Algorithms"     |
      | priority   | "HIGH"           |
      | dueAt      | "2030-05-10T12:00:00Z" |
      | notes      | "Focus on DP"    |
    Then the task is saved successfully
    And the task appears in the user’s list with status "open"

  @validation
  Scenario: Prevent creation when title is missing
    When the user creates a task with:
      | title      | ""                |
      | priority   | "LOW"             |
    Then the user sees a validation error "Title is required"
    And the task is not created

  @date-rule
  Scenario: Warn when deadline is in the past
    When the user creates a task with:
      | title      | "Finish lab"      |
      | dueAt      | "2000-01-01T00:00:00Z" |
    Then the user sees a warning "Due date is in the past"
    And the user must confirm or change the date
