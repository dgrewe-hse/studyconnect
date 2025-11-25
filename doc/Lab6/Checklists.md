# Review Checklists

## A. Introductory Text
| ID | Question | Result (Yes/Partly/No) | Comment |
|----|----------|------------------------|---------|
| A1 | Is the purpose of StudyConnect clearly explained?| Yes| Under header "Purpose and Scope" |
| A2 | Is the introductory text structured logically?| Yes| Use of fitting headers and paragraphs|
| A3 | Are target users identified?| Yes| Under "Target users and Context" |
| A4 | Does the the introduction include an overview of what the StudyConnect app shall do?| Yes| Overview  explained under "Core Features"|
| A5 | Does the introduction include information about the prject structure or the architecture?| Yes |Under "Technology Stack"|


## B. Functional Requirements

| ID | Question | Result (Yes/Partly/No) | Comment |
|----|----------|------------------------|---------|
| B1 |Are the functional requirements written in the correct form? (The system shall...)| Yes | All functional requirements use the correct wording.|
| B2| Are all requirements are uniquely identifiable? | No | The requirements are grouped under fitting headers which are numbered. Each requirement however not|
| B3 | Do the functional requirements cover all core features from the StudyConnect description (personal tasks, groups, deadlines, motivation, exports)? | Yes | Sections 1–6 align well with “Core Features” and “High-Level Expectations”. |
| B4 | Are requirements grouped logically (User Management, Tasks, Groups, etc.)? | Yes |  Grouped into 6 logical groups.|
| B5 | Are requirements free of contradictions? | Yes | No conflicts found (e.g., roles vs. permissions are consistent). |
| B6 | Are requirements free of unnecessary duplicates? | Partly | Export to PDF/ICS appears both in Task Management und Accessibility/Integration. Could be merged. |
| B7 | Are requirements testable (measurable, verifiable)? | Partly | Terms like “securely” (User Management) and “non-intrusive reminders” (Deadline Awareness) are vague without clear criteria. |
| B8 | Are responsibilities of “group member” vs. “admin” clearly expressed? | Yes | Section 1 & 3 explicitly distinguish roles and permissions. |

## C. Initial Backend Entity Implementations
| ID | Question | Result | Comment |
|----|----------|---------|---------|
| **1. Entity Existence & Structure** |
| C1.1 | Do User, Group, and Task entities exist and are clearly defined? | Yes | All exist as TypeScript interfaces. |
| C1.2 | Are entities separated into clear files/folders (e.g., /models)? | No | All interfaces and enums are placed in a single huge file (`dataStore.ts`). |
| C1.3 | Is there a consistent naming convention for entities and attributes? | Yes | Naming is clean and consistent across interfaces. |
| C1.4 | Are DTOs or request/response models used? | No | No DTO layer. Controllers directly accept raw data. |
| **2. Attribute Completeness & Correctness** | ||||
| C2.1 | Do entities include essential attributes? | Yes | All fields present |
| C2.2 | Are optional attributes explicitly marked with “?”? | Yes | description, dueDate, assigneeId, etc. are correct. |
| **3. Relationships** | |||| 
| C3.1 | Are relationships between Users and Groups defined? | Yes | GroupMember entity + helper functions exist. |
| C3.2 | Is Group & Task relationship defined? | Yes | Tasks can reference groupId. |
| C3.3 | Are relationships between Users and Tasks defined? (for example creator & assignee) | Yes | creatorId mandatory, assigneeId optional. |
| **4. Code Quality** |
| C4.1 | Is code free of duplication? | No | Massive duplication: two full systems (in-memory + DB)? |
| C4.2 | Do the files have a reasonable length? | No | DataStore is extremely large (≈ 1000+ lines). |
| C4.3 | Is documentation or commenting provided? | Partly | Kept to minimum, but good to understand code better. |
| **5. Testing** |
| C5.1 | Is there some form of testing? | Yes | Unit tests, BDD |
| C5.2 | Are unit test commands clearly documented? | Yes | `npm run test:bdd`, `npm run test`, `test:watch`, single-file execution documented. |
| C5.3| Are BDD features, steps, and config structured clearly (Exercise 5)? | Yes | Features in `features`, steps in `steps`, support in `support`, dedicated `cucumber.js`. |
| **6. Error Handling & Security** |
| C6.1 | Are errors handled when retrieval/update fails? | Partly | Returns null instead of throwing; weak error handling. Example: in dataStore.ts |
| C6.2 | Are sensitive fields (password) securely stored? | No | Passwords stored in plain text (critical). | passwordfor users is saved as string. dataStore.ts
| C6.3 | Are secrets (DB credentials) properly externalized? | Yes | `.env` variables used conventionally. |
| **7. Consistency With Requirements** |
| C7.1 | Do entities fullfill functional requirements? | Yes | - |
| C7.2 | Does naming align with the StudyConnect specification? | Yes | Naming matches what is described in the requirements files.|
| C7.3 | Does implementation match stated behavior? | Yes | - |





