Feature: Receive Task Notifications
  As a logged-in user
  I want to receive notifications about important task events
  In order to stay informed and manage my deadlines effectively.

  Background:
    Given a User "Max Mustermann" with ID "user123" exists
    And a User "Anna Schmidt" with ID "user456" exists
    And a Group "Databases" with ID 1 exists
    And "Max Mustermann" and "Anna Schmidt" are members of Group "Databases"
    And "Max Mustermann" is logged in
    And today's date and time is "2025-11-15 09:00"

  # ----------------------------------------------------
  # Scenario 1: Notification for a task due soon (e.g., within 24 hours)
  # ----------------------------------------------------
  Scenario: Receive a notification for a task due within 24 hours
    Given the following task exists for user "user123":
      | title                 | deadline            | status |
      | Prepare Presentation  | 2025-11-16 08:00    | todo   |
    When the system checks for pending notifications for "user123"
    Then a new notification should be generated
    And the notification message should be "Your task 'Prepare Presentation' is due soon (2025-11-16 08:00)."
    And the notification type should be "due_soon"
    And the notification status should be "unread"

  # ----------------------------------------------------
  # Scenario 2: Notification for an overdue (expired) task
  # ----------------------------------------------------
  Scenario: Receive a notification for an overdue task
    Given the following task exists for user "user123":
      | title                 | deadline            | status |
      | Submit Essay Draft    | 2025-11-14 17:00    | todo   |
    When the system checks for pending notifications for "user123"
    Then a new notification should be generated
    And the notification message should be "Your task 'Submit Essay Draft' is overdue (2025-11-14 17:00)."
    And the notification type should be "overdue"
    And the notification should be marked as "high_priority"

  # ----------------------------------------------------
  # Scenario 3: No notification for a task that is not due soon
  # ----------------------------------------------------
  Scenario: Do not receive notifications for tasks far in the future
    Given the following task exists for user "user123":
      | title                 | deadline            | status |
      | Start Research Paper  | 2025-12-10 12:00    | todo   |
    When the system checks for pending notifications for "user123"
    Then no "due_soon" or "overdue" notification should be generated for this task

  # ----------------------------------------------------
  # Scenario 4: Notification for being assigned to a new team task
  # ----------------------------------------------------
  Scenario: Receive a notification when added to a new team task
    Given "Max Mustermann" has no unread notifications
    When "Anna Schmidt" creates a new task in Group "Databases" and assigns it to "user123":
      | title                 | deadline            | assigner_id | group_id |
      | Review ER-Diagram     | 2025-11-20 17:00    | user456     | 1        |
    Then a new notification should be generated for "user123"
    And the notification message should be "Anna Schmidt assigned you to a new task: 'Review ER-Diagram'."
    And the notification type should be "new_assignment"

  # ----------------------------------------------------
  # Scenario 5: Viewing and marking notifications as read
  # ----------------------------------------------------
  Scenario: View notification list and mark as read
    Given the following unread notifications exist for "user123":
      | message                                      | type     |
      | "Your task 'Submit Essay Draft' is overdue." | overdue  |
      | "Your task 'Prepare Presentation' is due soon." | due_soon |
    When "Max Mustermann" views their notification list
    Then the list should display 2 notifications
    And the notification "Your task 'Submit Essay Draft' is overdue." should be visible
    And all displayed notifications should be marked as "read"
