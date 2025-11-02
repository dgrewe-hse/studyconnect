# BDD Test Automation

## Overview
In this file we'll find how to run the tests for the BDD Automation

---

## How to Run
Install dependencies (if not already installed):

```bash
cd apps/api
npm install
```

Run all BDD scenarios:

```bash
npm run test:bdd
```

The command executes every `.feature` file under:

```
apps/api/test/features/
```

---

## Tested Features
- **Create Task** → validates title, due date, and successful creation.  
- **Change Task Status** → checks valid and invalid status transitions.  
- **Assign Task** → ensures only admins can assign and non-members are rejected.

---
