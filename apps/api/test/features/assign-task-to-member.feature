Feature: Assign Task to Group Member
  As a group admin
  I want to assign a task to a group member
  So that responsibilities are clear

  Background:
    Given a group "SE Project" with members:
      | role    | user         |
      | admin   | alice@uni.de |
      | student | bob@uni.de   |
    And a task "Design schema" exists in group "SE Project"

  @happy
  Scenario: Admin assigns task to existing group member
    Given the current user is "alice@uni.de"
    When the user assigns "Design schema" to "bob@uni.de"
    Then the task "Design schema" shows assignee "bob@uni.de"

  @authz
  Scenario: Non-admin cannot assign
    Given the current user is "bob@uni.de"
    When the user assigns "Design schema" to "bob@uni.de"
    Then the user sees "Forbidden"

  @membership
  Scenario: Cannot assign to non-member
    Given the current user is "alice@uni.de"
    When the user assigns "Design schema" to "carol@uni.de"
    Then the user sees "User is not a member of this group"
