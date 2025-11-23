# Review of group 3

## 1. Review Type: Technical Review conducted by peers
Our team has chosen a Hybrid Approach that leverages the structure and objectives of a Technical Review while utilizing our team members as Peers. The review is conducted by our team members as equally competent "Peers", which is appropriate as we are all peers in the Software Testing lecture with similar expertise and knowledge.

## 2. Review Object
**Repository under review:**  
https://github.com/To-Kar/studyconnect

**Artifacts in scope:**  
1. Introductory text / problem description of StudyConnect  
2. Functional Requirements & Non-functional Requirements  
3. Domain Model (entities, relationships)  
4. Initial backend entity implementations (User, Group, Task, …)

## 3. Reiew Roles and Responsibilities

| Role | Team Member | Responsibilities |
| :--- | :--- | :--- |
| Moderator | - | Organizes review, Review Masterplan, Leads meeting, ensures structure, timeboxing, enforces review rules. |
| Reviewer 1 (Requirements) | - | Checks Requirements, Domain Model, testability, consistency. |
| Reviewer 2 (Code) |- | Checks Entities, relations, naming, annotations, implementation quality. |
| Notetaker | Reviewer 1 and 2 | Documents defects, decisions, open questions, meeting minutes. |


## 4. Review Goals
The primary goal of this Technical Review is to confirm the technical correctness and readiness of the artifacts provided by the Author Group Team 3. The inspection focuses on ensuring the product is robust, compliant, and fit for the purpose of the StudyConnect application.

- **Requirements**: Identify inconsistencies, missing details, and ambiguities.

- **Testability**: Ensure requirements are clear and testable.

- **Data Model**: Verify the correctness and completeness of the data model structure.

- **Code Defects**: Detect technical defects in entity implementations (structure, relations, validation).

- **Vision Check**: Assess overall clarity, maintainability, and alignment with the StudyConnect vision.

## 5. Review Process
### 5.1 Planning
- Define scope, roles, and timeline (this document).
- Confirm templates used: `Masterplan.md`,`CHECKLIST.md`, `List of Findings.md`, `Review Report.md`, `Retrospective.md`

### 5.2 Kick-off
- Align understanding of the artifacts to be reviewed.
- Clarify terminology.
- Confirm expectations for findings and severity levels.

### 5.3 Individual Preparation
- Each reviewer clones the Group 3 repository.
- Each reviewer reviews the requirements, domain model, and entity code.
- Each reviewer marks the checklist and notes potential defects for the review meeting.
- *Preparation time:* approx. 60–90 minutes per reviewer.

### 5.4 Review Meeting
- 30–45 minutes, led by the Moderator.
- Agenda: Requirements findings, Domain Model findings, Entities / Implementation findings, and Cross-cutting concerns (consistency, naming).
- The Notetaker documents all confirmed defects in `List of Findings.md`.

### 5.5 Rework (Simulated)
- Since we cannot modify the other group’s repository, we will simulate the expected corrections: classify defects as Fixed / Won’t Fix / Needs Clarification and refine severity if needed.

### 5.6 Follow-up
- Verify completeness of the defect log.
- Ensure all findings are clearly documented.
- Write the final retrospective (`Review Report.md`).

### 5.7 Retrospective
- Our team will reflect on the review
- Are reviews a suitable method for our team?
- If so for what area would we use them?


