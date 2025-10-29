# UC04 — Assign Group Task to Members

## Summary
- **Primary actor**: Group Administrator
- **Goal**: Create a group task and assign it to one or more members
- **Scope**: StudyConnect Web App (Group context)
- **Level**: User goal

## One-sentence definition
Group Administrator is assigning a group task to selected members to clarify responsibilities and deadlines.

## Stakeholders and Interests
- **Admin**: Wants to coordinate group work and clarify who does what by when
- **Assignees**: Want clear expectations and timely notifications
- **System**: Must enforce permissions and notify stakeholders

## Preconditions
- User is authenticated and is a Group Admin of the selected group
- Group has at least one member besides Admin

## Trigger
- Admin clicks "Add Task" on the group page

## Main Success Scenario (Basic Flow)
1. System displays group task form
2. Admin enters Title, Due Date, Priority, and Description/Notes
3. Admin selects one or more assignees from the member list
4. Admin clicks "Assign Task"
5. System validates inputs and permissions
6. System creates the task in group context with assigned_to members
7. System sends task assignment notifications to assignees
8. Task appears in group task list and in assignees' personal task views

## Alternative / Extension Flows
- 3a. No assignee selected → System shows validation error; Admin must select at least one
- 3b. Assign to all members (bulk) → System supports multi-select; proceed
- 5a. Admin-only assignment setting enforced → If disabled, show policy error and block
- 7a. Notification delivery failure → System logs and shows warning; task still created

## Postconditions
- Group task is persisted with group_id and assigned_to list
- Notifications queued/sent to all assignees

## Business Rules
- Permissions depend on group setting: assignment admin-only or all members
- Comments and collaboration are enabled on group tasks

## Non-Functional Requirements
- Creation and assignment < 1s, notifications near-real-time
- Clear visual indicators for assignees and due status (overdue, due soon)

## Success Metrics
- Assignment acceptance/read rates via notifications
- On-time completion rate for assigned tasks

## Notes for ATDD/BDD
- Scenarios around permission matrix, multi-assignment, and notification assertions

