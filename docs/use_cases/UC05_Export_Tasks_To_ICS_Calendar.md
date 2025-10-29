# UC05 — Export Tasks to ICS Calendar

## Summary
- **Primary actor**: Student
- **Goal**: Export personal (and eligible group) tasks to an ICS file for external calendars
- **Scope**: StudyConnect Web App
- **Level**: User goal

## One-sentence definition
Student is exporting tasks to an ICS file to view deadlines in an external calendar.

## Stakeholders and Interests
- **Student**: Wants consolidated calendar view across apps
- **System**: Must produce standards-compliant ICS safely and accurately

## Preconditions
- User is authenticated
- User has at least one task with a due date

## Trigger
- User selects "Export" → "Calendar (ICS)"

## Main Success Scenario (Basic Flow)
1. System opens export dialog with options (date range, include group tasks)
2. User selects desired options
3. User confirms export
4. System generates ICS events from tasks (due date/time, title, description)
5. System downloads the `.ics` file to the user’s device
6. User imports the ICS file into an external calendar

## Alternative / Extension Flows
- 1a. No tasks in selected range → System warns and allows export (empty or minimal file)
- 2a. Include group tasks disabled due to membership → System explains restriction
- 4a. ICS generation error → System shows error and allows retry

## Postconditions
- ICS file is generated per selected filters
- No data beyond selected scope is included

## Business Rules
- Include only tasks user has access to; respect group visibility rules
- Map priority and status to event details (e.g., description or categories)

## Non-Functional Requirements
- Generation time < 2s for up to 1000 tasks
- ICS is RFC 5545 compliant
- Data privacy is maintained (no private comments exported)

## Success Metrics
- Import success across common calendars (Google, Outlook, Apple)
- Low error/complaint rates for ICS formatting

## Notes for ATDD/BDD
- Scenarios for date range filtering, inclusion of group tasks, and empty exports

