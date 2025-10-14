# StudyConnect – System Context Diagram

## Overview
The following diagram illustrates the main actors and external systems that interact with the StudyConnect application.  
It also highlights the internal architecture layers (frontend, backend, database), aligned with the 3-tier model used in this project.

```mermaid
flowchart LR

  linkStyle default stroke-width:1.5px,stroke:#94a3b8

  subgraph EXT[External]
    direction TB
    student([Student])
    admin([Group Admin])
    cal[[Calendar App<br/>]]
    mail[[Mail / Push Service]]
    pdf[[PDF Viewer]]
  end

  subgraph SYS[StudyConnect]
    direction LR
    fe[Frontend<br/>Next.js ]
    be[Backend<br/>NestJS ]
    db[(PostgreSQL<br/>TypeORM)]
  end

  student --> fe
  admin --> fe

  fe <--> be
  be --> db

  be -- Export ICS --> cal
  be -- Reminder Notifications --> mail
  be -- Export PDF --> pdf

  student -. uses .- admin
  cal -. external .- mail
  mail -. external .- pdf

  classDef actor       fill:#e0f2fe,stroke:#0ea5e9,color:#0c4a6e,stroke-width:1.2px;
  classDef external    fill:#f1f5f9,stroke:#475569,color:#0f172a,stroke-width:1.2px;
  classDef internal    fill:#eef2ff,stroke:#6366f1,color:#111827,stroke-width:1.2px;
  classDef database    fill:#fff7ed,stroke:#ea580c,color:#7c2d12,stroke-width:1.2px;

  class student,admin actor
  class cal,mail,pdf external
  class fe,be internal
  class db database
```

Explanation

Frontend: Provides the user interface for students and admins to manage tasks and groups.
Backend: Contains business logic, validation, authentication, role-based access, and scheduled jobs (e.g., marking overdue tasks).
Database: Stores users, groups, tasks, comments, and notifications.
External Services:
Calendar apps (Google, Apple, Outlook) receive ICS exports.
PDF viewers display exported task summaries.
Mail or push services send non-disruptive reminders.
Actors:
Student: Creates and manages personal and group tasks.
Group Admin: Manages memberships, assigns tasks, moderates activities.


---

markdown
# StudyConnect – Use Case Diagram (Task Management)

## Scope
This diagram focuses on the **Task Management** feature of StudyConnect, which enables students to create, update, and track tasks individually or within groups.

```mermaid
flowchart TB
  linkStyle default stroke-width:1.4px,stroke:#64748b

  student[Student]
  admin[Group Admin]
  cron[System Timer / Cron]

  UC_Create[Create Task]
  UC_Deadline[Set Deadline]
  UC_Categorize[Categorize Task]
  UC_Edit[Edit Task]
  UC_Change[Change Task Status]

  UC_Comment[Add Comment]
  UC_View[View Tasks]
  UC_Overdue[Highlight Overdue Tasks]

  UC_Assign[Assign Task to Member]
  UC_Export[Export as PDF/ICS]

  student --> UC_Create
  student --> UC_Edit
  student --> UC_Change
  student --> UC_Categorize
  student --> UC_Deadline
  student --> UC_Comment
  student --> UC_View
  student --> UC_Overdue
  student --> UC_Export

  admin --> UC_Assign
  admin --> UC_View
  admin --> UC_Export

  cron --> UC_Overdue

  UC_Create -. includes .-> UC_Deadline
  UC_Create -. includes .-> UC_Categorize
  UC_Edit   -. extends  .-> UC_Change

  classDef actor fill:#e0f2fe,stroke:#0ea5e9,color:#0c4a6e,stroke-width:1.2px;
  classDef uc fill:#f8fafc,stroke:#0891b2,color:#083344,stroke-width:1.2px;

  class student,admin,cron actor
  class UC_Create,UC_Edit,UC_Change,UC_Categorize,UC_Deadline,UC_Comment,UC_View,UC_Overdue,UC_Assign,UC_Export uc

```
Description
Primary actor: Student
Secondary actors: Group Admin, System Timer (Cron)
Goal: Efficient management of personal and group study tasks with visibility of deadlines and progress.
Includes: Setting deadlines and categories during task creation.
Extends: Editing tasks includes changing status.


---

markdown
# StudyConnect – Use Case Descriptions

---

## UC-01: Create Task
**Primary Actor:** Student  
**Goal:** Create a new task with title, category, priority, notes, and optional deadline.  
**Preconditions:** User is authenticated.  
**Postconditions:** Task is persisted and visible in the user’s or group’s list.  

### Main Success Scenario
1. Student opens “Create Task”.
2. Enters title (mandatory).
3. Optionally sets category, priority, deadline, and notes.
4. Confirms creation.
5. System validates data and saves the task.
6. Confirmation message and task overview are displayed.

### Extensions
- 2a. Missing title → error message.  
- 4a. Deadline in the past → warning, user must confirm or change date.  
- 5a. Network error → retry suggestion or local draft.  

---

## UC-03: Change Task Status
**Primary Actor:** Student  
**Supporting Actor:** System Timer (Cron)  
**Goal:** Update the task’s progress state (open, in progress, completed, overdue).  
**Preconditions:** Task exists, user has permission.  
**Postconditions:** New status saved, UI and filters updated.

### Main Success Scenario
1. User opens a task.
2. Chooses new status.
3. System validates and updates the task.
4. UI refreshes, showing updated badge or filter.

### Extensions
- 2a. Invalid transition → warning.  
- 3a. No permission → “Forbidden”.  
- 5a. Cron automatically marks task as overdue if `dueAt < now` and status ≠ completed.  

---

## UC-09: Assign Task to Group Member
**Primary Actor:** Group Admin  
**Goal:** Assign an existing task to a specific group member.  
**Preconditions:** User has admin role and group membership exists.  
**Postconditions:** Task shows assigned member; optional reminder created.

### Main Success Scenario
1. Admin opens group task.
2. Selects member to assign.
3. System validates permissions.
4. System updates assignment and saves.
5. UI displays the assigned member.

### Extensions
- 3a. Not an admin → “Forbidden”.  
- 2a. Member not in group → prompt to invite first.  

---

## UC-10: Export as PDF/ICS
**Primary Actor:** Student  
**Goal:** Export tasks as calendar or printable overview.  
**Preconditions:** Tasks exist, user has read access.  
**Postconditions:** Exported file successfully downloaded.

### Main Success Scenario
1. User selects export option and filters (e.g. group, timeframe).
2. System generates PDF or ICS.
3. File is downloaded or opened.

### Extensions
- 2a. No tasks found → info message.  
- 3a. Import failure (ICS) → user guidance message.  
