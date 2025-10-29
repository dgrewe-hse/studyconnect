@focus
Feature: UC05 Export Tasks to ICS Calendar
  Student is exporting tasks to an ICS file to view deadlines in an external calendar.

  Background:
    Given a Student is authenticated
    And tasks exist for the Student

  Scenario: Export personal tasks within a date range
    When the Student opens the Export dialog
    And the Student selects format "ICS"
    And the Student selects date range "2025-11-01" to "2025-11-30"
    And the Student enables "Include group tasks"
    And the Student confirms export
    Then an ICS file is generated and downloaded
    And events include tasks due within the selected range
    And events include group tasks the Student has access to

  Scenario: Export when no tasks in range
    When the Student opens the Export dialog
    And the Student selects format "ICS"
    And the Student selects date range "2024-01-01" to "2024-01-02"
    And the Student confirms export
    Then an ICS file is generated with no events or a minimal calendar

  Scenario: Respect group visibility restrictions
    Given the Student is not a member of group "Private Group"
    When the Student exports with "Include group tasks"
    Then events from "Private Group" are not included

  Scenario Outline: Validate options and handle generation errors
    When the Student opens the Export dialog
    And the Student selects format <format>
    And the Student selects date range <from> to <to>
    And the Student toggles include group tasks <includeGroups>
    And the Student confirms export
    Then the export result is <result>

    Examples:
      | format | from         | to           | includeGroups | result                         |
      | "ICS" | "2025-11-01" | "2025-11-30" | "on"          | "Success: file downloaded"     |
      | "ICS" | "invalid"    | "2025-11-30" | "off"         | "Error: invalid date range"    |
      | "ICS" | "2025-11-01" | "invalid"    | "on"          | "Error: invalid date range"    |
      | "ICS" | "2025-11-01" | "2025-11-30" | "on"          | "Failure: generation error; retry allowed" |

