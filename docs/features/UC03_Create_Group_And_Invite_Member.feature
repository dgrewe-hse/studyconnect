Feature: UC03 Create Study Group and Invite Member
  Group Administrator is creating a study group and inviting a member to coordinate collaboration.

  Background:
    Given a Student is authenticated
    And the Student has permission to create groups

  Scenario: Create group with valid settings and send invitation
    When the Student opens the Create Group form
    And the Student enters name "Algorithms Study"
    And the Student enters description "Weekly prep and problem-solving"
    And the Student selects visibility "Private"
    And the Student sets maximum members to 20
    And the Student sets settings: join approval required, assignment admin-only, comments members-only
    And the Student creates the group
    Then the group "Algorithms Study" is created with the Student as Admin
    When the Admin invites "alice@example.com"
    Then an invitation to "alice@example.com" is recorded with expiry of 7 days

  Scenario Outline: Validate name/description/limit and invitation addresses
    When the Student opens the Create Group form
    And the Student enters name <name>
    And the Student enters description <description>
    And the Student sets maximum members to <max>
    And the Student creates the group
    Then the form shows <createValidation>
    When the Admin invites <invitee>
    Then the invitation validation shows <inviteValidation>

    Examples:
      | name           | description | max | createValidation                        | invitee              | inviteValidation                 |
      | ""             | "short"     | 10  | "Name is required"                      | "alice@example.com" | "n/a"                            |
      | "A...101"      | "valid"     | 10  | "Name must be <= 100 chars"             | "invalid-email"     | "Invalid email/username"        |
      | "Valid"       | "D...501"    | 10  | "Description must be <= 500 chars"      | "alice@example.com" | "n/a"                            |
      | "Valid"       | "valid"      | 51  | "Maximum members cannot exceed 50"      | "alice@example.com" | "n/a"                            |
      | "Valid"       | "valid"      | 20  | "Success"                               | "alice@example.com" | "Invitation recorded"            |

  Scenario: Prevent duplicate invitation
    Given the group "Algorithms Study" exists and Admin invited "alice@example.com"
    When the Admin invites "alice@example.com"
    Then the system warns about a duplicate invitation and prevents sending

  Scenario: Invitation delivery failure is reported but group remains
    Given the group "Algorithms Study" exists
    When the Admin invites "bob@example.com" and delivery fails
    Then the system shows a delivery error and suggests retry
    And the group remains unchanged

