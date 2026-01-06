### Tasks API (v1)

Base URL (local development): `http://localhost:8080`

All task endpoints are versioned under: `/api/v1/tasks`

For now, the **current user** is identified via the `X-User-Id` request header.

---

### List personal tasks

- **Method**: `GET`
- **URL**: `/api/v1/tasks`
- **Headers**:
  - `X-User-Id`: numeric user ID (e.g. `1`)

```bash
curl -X GET "http://localhost:8080/api/v1/tasks?page=0&size=20" \
  -H "X-User-Id: 1" \
  -H "Accept: application/json"
```

---

### Get task by id

- **Method**: `GET`
- **URL**: `/api/v1/tasks/{id}`

```bash
curl -X GET "http://localhost:8080/api/v1/tasks/10" \
  -H "Accept: application/json"
```

---

### Create a new personal task

- **Method**: `POST`
- **URL**: `/api/v1/tasks`

**Request headers**:

- `X-User-Id: 1`
- `Content-Type: application/json`

**Request body**:

```json
{
  "title": "Prepare math exam",
  "description": "Review chapters 1–5 and solve practice problems",
  "dueDate": "2025-06-30T18:00:00",
  "priority": "HIGH",
  "category": "Mathematics"
}
```

**Example cURL**:

```bash
curl -X POST "http://localhost:8080/api/v1/tasks" \
  -H "X-User-Id: 1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Prepare math exam",
    "description": "Review chapters 1–5 and solve practice problems",
    "dueDate": "2025-06-30T18:00:00",
    "priority": "HIGH",
    "category": "Mathematics"
  }'
```

---

### Create a new group task

- **Method**: `POST`
- **URL**: `/api/v1/tasks`

**Request body** (extra fields `groupId`, `assignedToId`):

```json
{
  "title": "Solve Set A",
  "description": "First group problem set",
  "dueDate": "2025-11-10T10:00:00",
  "priority": "MEDIUM",
  "category": "Algorithms",
  "groupId": 5,
  "assignedToId": 2
}
```

**Example cURL**:

```bash
curl -X POST "http://localhost:8080/api/v1/tasks" \
  -H "X-User-Id: 1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Solve Set A",
    "description": "First group problem set",
    "dueDate": "2025-11-10T10:00:00",
    "priority": "MEDIUM",
    "category": "Algorithms",
    "groupId": 5,
    "assignedToId": 2
  }'
```

---

### Update an existing task (full update)

- **Method**: `PUT`
- **URL**: `/api/v1/tasks/{id}`

**Request body** (any field omitted will stay unchanged):

```json
{
  "title": "Prepare math exam (updated)",
  "description": "Review chapters 1–6 and focus on dynamic programming",
  "dueDate": "2025-07-01T18:00:00",
  "priority": "HIGH",
  "status": "IN_PROGRESS",
  "category": "Mathematics",
  "assignedToId": 2
}
```

**Example cURL**:

```bash
curl -X PUT "http://localhost:8080/api/v1/tasks/10" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Prepare math exam (updated)",
    "description": "Review chapters 1–6 and focus on dynamic programming",
    "dueDate": "2025-07-01T18:00:00",
    "priority": "HIGH",
    "status": "IN_PROGRESS",
    "category": "Mathematics",
    "assignedToId": 2
  }'
```

---

### Update task status only

- **Method**: `PATCH`
- **URL**: `/api/v1/tasks/{id}/status`

**Request body**:

```json
{
  "status": "COMPLETED"
}
```

**Example cURL**:

```bash
curl -X PATCH "http://localhost:8080/api/v1/tasks/10/status" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "COMPLETED"
  }'
```

---

### Delete a task

- **Method**: `DELETE`
- **URL**: `/api/v1/tasks/{id}`

```bash
curl -X DELETE "http://localhost:8080/api/v1/tasks/10"
```

