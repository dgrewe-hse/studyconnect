# UC02 — Edit and Complete Personal Task

## Summary
- **Primary actor**: Student
- **Goal**: Update an existing personal task and mark it as completed
- **Scope**: StudyConnect Web App
- **Level**: User goal

## One-sentence definition
Student is updating a personal task and marking it complete to reflect progress.

## Stakeholders and Interests
- **Student**: Wants to keep task details accurate and close task when done
- **System**: Must maintain integrity and history of task changes

## Preconditions
- User is authenticated as a Student
- At least one task exists, visible in `My Tasks`

## Trigger
- User selects an existing task from the list

## Main Success Scenario (Basic Flow)
1. System displays the task details panel or edit dialog
2. User edits Title, Due Date, Priority, Notes, Category, or Tags
3. User clicks "Save"
4. System validates changes and updates the task
5. System confirms success and shows updated task in list
6. User marks the task as "Completed"
7. System sets status to Completed and reflects completion in list and metrics

## Alternative / Extension Flows
- 2a. User changes due date to past date → System warns but allows; proceed
- 3a. Validation fails (e.g., Title empty) → Show inline error; user fixes; resume at step 2
- 6a. User toggles completion back to Open → System reverts status and updates indicators
- 7a. Network/API failure → Show retry option; on retry, resume at the failed step

## Postconditions
- Task data is updated and persisted
- Completion is reflected in list view (green checkmark) and calendar
- Personal metrics include the completion event

## Business Rules
- Status transitions: Open ↔ In Progress ↔ Completed
- Completed tasks can be re-opened

## Non-Functional Requirements
- Update response < 1s (P0)
- Clear visual feedback for status changes and error states
- Accessible keyboard navigation for toggles and save

## Success Metrics
- Reduction in stale tasks (more on-time completions)
- High success rate of edits without errors

## Notes for ATDD/BDD
- Scenarios for each editable field, status toggling, and validation messages

