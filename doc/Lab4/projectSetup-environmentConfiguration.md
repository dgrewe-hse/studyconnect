# Exercise 4.1 — StudyConnect API Setup & Testing

How to run and test the **StudyConnect API** backend located in `apps/api/`.

## Requirements
- Node.js ≥ 18
- npm ≥ 9
- Docker & Docker Compose
- Git

## Setup

```bash
# Clone repo
git clone https://github.com/Ricardo0919/studyconnect-software-testing.git
cd studyconnect-software-testing

# Start DB containers (Postgres + pgAdmin)
docker compose up -d

# Go to backend and install dependencies
cd apps/api
npm install
```

Create a `.env` file in `apps/api/`:
```
PORT=3001
DATABASE_HOST=localhost
DATABASE_PORT=5432
DATABASE_USER=postgres
DATABASE_PASSWORD=postgres
DATABASE_NAME=studyconnect
DB_SYNC=true
DB_LOGGING=true
```

## ▶️ Run the API
```bash
npm run start:dev
```
App: [http://localhost:3001](http://localhost:3001)  
Health checks: `/health` and `/health/ready`

## 🧪 Testing
```bash
# Unit tests
npm test

# End-to-end tests
npm run test:e2e
```

## 🧹 Stop services
```bash
docker compose down
```