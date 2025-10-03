# Tech Stack Decisions – StudyConnect

## Team Members
- Annabel Heberle  
- Artur Hoxha  
- Ricardo Sierra Roa 

---

## Overview
The goal of **StudyConnect** is to provide students with a simple tool to manage tasks, collaborate in groups, and keep track of progress. Since the course emphasizes **software testing** and a clear **3-tier architecture**, our team selected technologies that balance ease of use, strong testing support, and scalability.

---

## Frontend: Next.js (with TypeScript)
**Reasons for choice:**
- Familiar to the team.
- Supports modern React features (App Router, Server Components).
- Excellent developer experience and ecosystem (TailwindCSS, shadcn/ui).
- Compatible with unit testing (Vitest + React Testing Library) and end-to-end testing (Playwright/Cypress).

---

## Backend: NestJS (with TypeScript)
**Reasons for choice:**
- Enforces modular, layered architecture (controllers, services, modules).
- Strong support for testing (Jest, Supertest).
- Built-in support for guards (roles/permissions), pipes (validation), and interceptors (logging).
- Industry-standard and recommended for team projects.

---

## Database: PostgreSQL with TypeORM
**Reasons for choice:**
- Relational database fits the project model (Users ⇄ Groups ⇄ Tasks).
- TypeORM is officially supported in NestJS, easy to configure, and widely known.
- Provides clear entity definitions and migrations that can be reviewed and tested.
- Supports **SQLite in-memory** for fast automated tests (unit and integration).
- Transparent for white-box/black-box test design.

---

## Testing Strategy
- **Unit Testing:**
  - Backend: Jest + Nest TestingModule with SQLite in-memory.
  - Frontend: Vitest + React Testing Library.
- **Integration Testing:**
  - Supertest for API endpoints.
  - SQLite or PostgreSQL test container for DB integration.
- **End-to-End Testing:**
  - Playwright or Cypress for UI flows (login, task creation, overdue state).
- **Static Testing:**
  - ESLint, Prettier, Husky + lint-staged.
- **CI/CD Integration:**
  - GitHub Actions: run migrations, build, and test pipelines.

---

## Project Organization
- **Monorepo structure** (optional, using Turborepo):
/apps
/web (Next.js frontend)
/api (NestJS backend)
/packages
/types (shared DTOs and schemas)
/ui (optional shared components)
/doc (project documentation)

- **Work split:**
- Developer A: Database + Entities + Migrations.
- Developer B: Authentication + Roles/Guards.
- Developer C: Tasks/Groups module + Frontend integration.

---

## Risks and Mitigation
- **Learning curve with NestJS/TypeORM:**
- Use official templates and guides.
- Start with minimal modules (`auth`, `users`, `groups`, `tasks`).
- **Setup complexity:**
- Use Docker Compose for PostgreSQL in development.
- Use SQLite in tests to avoid dependency on external DB.
- **Overdue task logic:**
- Implement initially “on read” (status computed dynamically).
- Optionally add a scheduled job later if needed.

---

## Final Decision
We chose **Next.js + NestJS + PostgreSQL (TypeORM)**.  
This stack ensures testability, clarity in architecture, and alignment with the course topics.
