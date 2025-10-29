Feature: UC04 Assign Group Task to Members
  Group Administrator is assigning a group task to selected members to clarify responsibilities and deadlines.

  Background:
    Given a Student is authenticated
    And the Student is Admin of the group "Algorithms Study" with members:
      | member |
      | Alice  |
      | Bob    |

  Scenario: Create a group task and assign to multiple members
    When the Admin opens the Add Task form in group "Algorithms Study"
    And the Admin enters title "Solve Set A"
    And the Admin selects due date "2025-11-10"
    And the Admin selects priority "High"
    And the Admin selects assignees:
      | member |
      | Alice  |
      | Bob    |
    And the Admin assigns the task
    Then the group task "Solve Set A" is created with assignees "Alice, Bob"
    And assignment notifications are sent to "Alice, Bob"
    And the task appears in assignees' personal task lists

  Scenario: Require at least one assignee
    When the Admin opens the Add Task form in group "Algorithms Study"
    And the Admin enters title "Unassigned"
    And the Admin assigns the task without selecting assignees
    Then the form shows "Select at least one assignee"

  Scenario: Enforce admin-only assignment policy
    Given the group policy is "assignment admin-only"
    When a non-admin member tries to assign a task
    Then the action is blocked with "Only admins can assign tasks"

  Scenario Outline: Multi-select and bulk assignment
    When the Admin opens the Add Task form in group "Algorithms Study"
    And the Admin enters title <title>
    And the Admin selects due date <dueDate>
    And the Admin selects assignees <assignees>
    And the Admin assigns the task
    Then the task <title> is created with assignees <assignees>

    Examples:
      | title            | dueDate       | assignees      |
      | "Solve Set B"   | "2025-11-12" | "Alice"        |
      | "Solve Set C"   | "2025-11-15" | "Alice, Bob"   |

