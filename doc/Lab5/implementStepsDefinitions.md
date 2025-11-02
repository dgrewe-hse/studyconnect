# Implement Step Definitions

## Project Structure

**Step definition files (TypeScript):**
- [assign-task-to-member.steps.ts](../../apps/api/test/steps/assign-task-to-member.steps.ts)
- [change-task-status.steps.ts](../../apps/api/test/steps/change-task-status.steps.ts)
- [create-task.steps.ts](../../apps/api/test/steps/create-task.steps.ts)
- [common.steps.ts](../../apps/api/test/steps/common.steps.ts) — shared assertions
- [world.ts](../../apps/api/test/steps/support/world.ts) — Cucumber world context
- [hooks.ts](../../apps/api/test/steps/support/hooks.ts) — Before/After setup
---

## Scenarios Implemented

1) **Assign Task to a Group Member**  
   - File: `assign-task-to-member.feature`  
   - Tests admin-to-member assignment, non-admin restriction, and rejection for non-members.

2) **Change Task Status**  
   - File: `change-task-status.feature`  
   - Valid transitions (`OPEN → IN_PROGRESS → COMPLETED`),  
     rejection for invalid ones (`COMPLETED → OPEN`),  
     and ensures the task keeps its original status.

3) **Create Task with Validations**  
   - File: `create-task.feature`  
   - Covers valid creation, missing title validation, and warning when the due date is in the past.

---

