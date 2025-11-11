# User Stories Definition

## Scope
Three acceptance features for **StudyConnect**, aligned with existing **Use Cases (UC)** and **Functional Requirements (FR)**.

## Features
1. **Create Task**  
   Covers: FR-1 (create tasks with title/deadline/priority/notes), FR-4 (categories).  
   UC: [UC-01 Create Task](../../apps/api/test/features/create-task.feature)  
2. **Change Task Status (+Overdue)**  
   Covers: FR-3 / FR-5 (open → in progress → completed) and FR-13 (highlight overdue).  
   UC: [UC-03 Change Task Status](../../apps/api/test/features/change-task-status.feature)  
3. **Assign Task to Group Member**  
   Covers: FR-6…FR-9 (roles/admin, membership, assignment).  
   UC: [UC-09 Assign Task to Group Member](../../apps/api/test/features/assign-task-to-member.feature)

## Traceability
- **Functional Requirements:** see [`requirements.md`](../Lab2/requirements.md)  
  (Task Management, Group Collaboration, Deadlines & Notifications).  
- **Use Cases and Extensions:** see [`systemcontext-usecase.md`](../Lab2/systemcontext-usecase.md)

## Dependencies
Install the required BDD packages:

```bash
npm install -D @cucumber/cucumber cucumber-html-reporter ts-node typescript
```
