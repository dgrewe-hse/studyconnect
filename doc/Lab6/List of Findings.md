| **Individual Preparation** |   |   |   | **Review Meeting** |   |   | |  |
|---------------------------|---|---|---|--------------------|---|---| ---| ----|
| **Review Object** | **Finding Location** | **Description**   | **Checklist /Scenario** | **Found by** | **Severity Level** | **Comments** | **Status** | **Responsible Person / Date** |
|         Requirements                  |      /docs/Assignment_2_Reqs_and_Quality_Goals.md    |     Requirements are not uniqely identifiable. |  B2  |        Annabel       |      Low   |        |         |                   |
| Requirements    | /docs/Assignment_2_Reqs_and_Quality_Goals.md  – User Management, Req. 1           | The requirement “The system shall allow users to register and log in securely.” is not testable as written. “Securely” is undefined (no criteria like hashing, password rules, or transport security).  | B7 | Annabel | Medium|  | | | 
| Requiremnets | /docs/Assignment_2_Reqs_and_Quality_Goals.md  – Deadline and Progress Awareness, Req.3 | The description “non-intrusive reminders” is not verifyable. What counts as non-intrusive or intrusive?  | B7 | Annabel | Medium | | | |
| Requirements | /docs/Assignment_2_Reqs_and_Quality_Goals.md - |  Export of data to PDF/ICS is mentioned under “Task and Goal Management” and again under “Accessibility and Integration”. This duplication may lead to inconsistencies later. | B6 | Annabel | Low | | | |
| Initial Backend | backend/src/lib/dataStore.ts | Entities are not seperated into different files and rather placed in on huge file | C1.2 | Annabel | Low | | | |
| Initial Backend | backend/src/lib/dataStore.ts and backend/src/lib/databaseService.ts | Backend structure could be better a little bit unclear -> see lib folder | C1.5 | Artur | Low | | | |
| Initial Backend | backend/src/lib/dataStore.ts | Entities are in two differnet files -> in memory and -> in Postgres | C4.1 | Artur | Low | | | |
| Initial Backend | backend/src/lib/dataStore.ts and backend/src/lib/databaseService.ts | Too long both over ~500 LOC | C4.2 | Artur | Low | | | |
| Initial Backend | backend/src/lib/dataStore.ts and backend/src/lib/databaseService.ts | In the data the password is not clearly shown that it is hashed, only if you look in the controllers, where the password is created  | C6.2 | Artur | Medium | | |
