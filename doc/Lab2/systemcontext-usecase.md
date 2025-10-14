# StudyConnect – System Context Diagram

## Overview
The following diagram illustrates the main actors and external systems that interact with the StudyConnect application.  
It also highlights the internal architecture layers (frontend, backend, database), aligned with the 3-tier model used in this project.

```mermaid
flowchart LR
  subgraph External
    U[Student]
    A[Group Admin]
    CAL[Calendar App]
    MAIL[Mail/Push Service]
    PDF[PDF Viewer]
  end

  subgraph StudyConnect
    FE[Frontend\nNext.js (TypeScript)]
    BE[Backend\nNestJS (TypeScript, REST API, RBAC)]
    DB[(PostgreSQL Database\nTypeORM ORM)]
  end

  U <--> FE
  A <--> FE
  FE <--> BE
  BE <--> DB

  BE -- Export ICS --> CAL
  BE -- Export PDF --> PDF
  BE -- Reminder Notifications --> MAIL

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

## 📄 **`docs/use_cases/use_case_diagram_task_management.md`**

```markdown
# StudyConnect – Use Case Diagram (Task Management)

## Scope
This diagram focuses on the **Task Management** feature of StudyConnect, which enables students to create, update, and track tasks individually or within groups.

```mermaid
flowchart TD
  %% Actors
  actorUser([Student])
  actorAdmin([Group Admin])
  actorCron([System Timer / Cron])

  %% Use Cases
  UC1((Create Task))
  UC2((Edit Task))
  UC3((Change Task Status))
  UC4((Categorize Task))
  UC5((Set Deadline))
  UC6((Add Comment))
  UC7((View Tasks))
  UC8((Highlight Overdue Tasks))
  UC9((Assign Task to Member))
  UC10((Export as PDF/ICS))

  %% Relationships
  actorUser --> UC1
  actorUser --> UC2
  actorUser --> UC3
  actorUser --> UC4
  actorUser --> UC5
  actorUser --> UC6
  actorUser --> UC7
  actorUser --> UC8
  actorUser --> UC10

  actorAdmin --> UC9
  actorAdmin --> UC7

  actorCron --> UC8

  %% include/extend notes
  UC1 ---|"include"| UC5
  UC1 ---|"include"| UC4
  UC2 ---|"extend"| UC3

Description
Primary actor: Student
Secondary actors: Group Admin, System Timer (Cron)
Goal: Efficient management of personal and group study tasks with visibility of deadlines and progress.
Includes: Setting deadlines and categories during task creation.
Extends: Editing tasks includes changing status.


---

## 📄 **`docs/use_cases/use_case_descriptions.md`**

```markdown
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
