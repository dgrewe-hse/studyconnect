# Quality Model (ISO 25010)

## Quality Atributes
### 1) Functional Suitability
- Abstract: Degree to which the app provides the functions students need.
- Specific: Create tasks (title/deadline/priority/notes), organize (groups & categories), track status (open/in progress/completed), group work (invite/remove/assign/moderate), task-scoped comments, dates & non-disruptive reminders, points/badges, export to PDF/ICS.
- Measurable: At release, 100% of the 15 functional requirements are implemented, the end-to-end core scenario passes ≥95%, and there are 0 critical defects in core flows.

### 2) Usability
- Abstract: Users achieve goals effectively and efficiently.
- Specific: Simple, clear UI; understandable statuses and categories; reminders that inform without overwhelming.
- Measurable: In a quick hallway test with 5 students, each can create and categorize one task without help within 1 minute and reports that reminders are not overwhelming.

### 3) Maintainability (incl. Modularity)
- Abstract: The system can be changed and extended easily.
- Specific: Modular, well-documented architecture with clear separation of concerns.
- Measurable: Adding a small field to “Task” requires edits in no more than three files and each module has a short README explaining its purpose and boundaries.

### 4) Compatibility / Interoperability
- Abstract: The system exchanges information with other tools.
- Specific: Reliable export to PDF and iCalendar (ICS) for integration into students’ workflows.
- Measurable: In the export test suite, ICS imports succeed on Google/Apple/Outlook and PDFs include all required fields, with 0% export errors.

## Measures to Guarantee Testability
- Checklist per requirement: for each FR, write 1–2 steps on “how to verify it” and mark done / not done.
- Time and test data: use a test clock and a tiny sample dataset (2–3 tasks, 1 group) so you can check “overdue” and reminders without waiting.
- Name things clearly: give buttons and fields fixed, clear IDs/names so they’re easy to find during tests.
- One main end-to-end run: a simple path: create task → categorize → change status → assign → export.
- Before merging changes: run the basic tests and merge only if they pass.
- Sample files: keep one ICS and one PDF example and check they open and show the expected info.