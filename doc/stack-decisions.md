# Tech Stack Decisions – StudyConnect

## Team Members
- Annabel Heberle  
- Artur Hoxha  
- Ricardo Sierra Roa 

---

## Overview
The goal of **StudyConnect** is to provide students with a simple tool to manage tasks, collaborate in groups, and keep track of progress. Since the course emphasizes **software testing** and a clear **3-tier architecture**, our team selected technologies that balance ease of use, strong testing support, and scalability.

This stack selection directly supports the learning objectives of the **Software Testing** course.  
By choosing modern, test-friendly frameworks, our goal is not only to build a functional application but also to apply **software quality principles** such as unit testing, integration testing, and continuous integration throughout the semester.  
The architecture enables us to demonstrate testing at all levels **frontend, backend, and database**, consistent with the course’s 3-tier requirement.

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

---

## Alignment with Course Objectives
Our chosen stack **Next.js + NestJS + PostgreSQL** supports every topic planned for the **Software Testing** module, including:
- Unit testing (Jest, Vitest)
- Integration and API testing (Supertest)
- End-to-end testing (Playwright/Cypress)
- Continuous Integration and code quality practices

This ensures that each development stage can be tested and documented according to the **Software Testing Lab** requirements, demonstrating our understanding of both implementation and software quality assurance.

