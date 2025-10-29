# UC01 — Create Personal Task

## Summary
- **Primary actor**: Student
- **Goal**: Create a new personal task with required details to track learning work
- **Scope**: StudyConnect Web App
- **Level**: User goal

## One-sentence definition
Student is creating a personal task to track study work efficiently.

## Stakeholders and Interests
- **Student**: Wants quick, intuitive task capture with minimal friction
- **System**: Must validate inputs and persist task reliably

## Preconditions
- User is authenticated as a Student
- User is on `My Tasks` page

## Trigger
- User clicks "Add Task"

## Main Success Scenario (Basic Flow)
1. System displays the new task form
2. User enters Title (<= 200 chars)
3. User selects Due Date (date and optional time)
4. User selects Priority (Low | Medium | High)
5. User optionally enters Notes (<= 1000 chars)
6. User optionally sets Category and Tags
7. User clicks "Save"
8. System validates inputs (lengths, formats)
9. System creates the task and stores it
10. System shows success feedback and the task appears in the list view

## Alternative / Extension Flows
- 3a. Invalid due date/time format → System shows inline error; user corrects; resume at step 3
- 5a. Notes exceed 1000 characters → System shows validation message; user edits; resume at step 5
- 7a. User cancels form → System discards input and returns to task list
- 8a. Network/API failure → System shows error toast with retry; on retry, resume at step 7

## Postconditions
- Task is persisted with status = Open, created_by = current user
- Task is visible in list, searchable, and included in calendar view

## Business Rules
- Title is required and must be unique only within user intent (no hard uniqueness enforced)
- Priority defaults to Medium if not specified
- Status defaults to Open

## Non-Functional Requirements
- Form submission response < 1s (P0)
- Accessible form controls (WCAG 2.1 AA)
- Clear validation messages and recovery options

## Success Metrics (from Spec)
- Reduced time-to-capture (< 20s median from open to save)
- Increased task creation adoption (DAU creating >= 1 task)

## Notes for ATDD/BDD
- Define Given/When/Then around validation boundaries (Title length, Notes length)
- Cover success, cancel, and network-retry behaviors

