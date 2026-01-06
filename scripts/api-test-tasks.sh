#!/usr/bin/env bash

##
## Smoke test script for the Tasks API of StudyConnect Backend.
##
## Endpoints are defined in TaskController:
##   - GET    /api/v1/tasks
##   - GET    /api/v1/tasks/{id}
##   - POST   /api/v1/tasks
##   - PUT    /api/v1/tasks/{id}
##   - PATCH  /api/v1/tasks/{id}/status
##   - DELETE /api/v1/tasks/{id}
##
## With the current test profile, the application runs on:
##   - server.port=8080
##   - server.servlet.context-path=/api
##
## So from outside the container, the full URLs are:
##   - http://localhost:8080/api/api/v1/tasks[...]
##
## NOTE:
##   The controller requires that the X-User-Id header refers to an
##   existing User in the database. If that user does not exist in
##   the H2 test database, requests will correctly return 404
##   ("User not found" or "Task not found").
##

set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"
USER_ID="${USER_ID:-1}"

# ANSI colors and symbols
GREEN="\033[0;32m"
RED="\033[0;31m"
YELLOW="\033[0;33m"
NC="\033[0m" # No Color

CHECK="✔"
CROSS="✖"
INFO="➜"

if ! command -v jq >/dev/null 2>&1; then
  echo -e "${RED}${CROSS} jq is required but not installed. Please install jq and retry.${NC}" >&2
  exit 1
fi

curl_check() {
  local step="$1"
  shift

  echo
  echo -e "${INFO} ${step}"

  local curl_exit=0
  local http_code

  set +e
  http_code=$(curl -sS -o /dev/stderr -w "%{http_code}" "$@")
  curl_exit=$?
  set -e

  if [[ "${curl_exit}" -ne 0 ]]; then
    echo -e "${RED}${CROSS} ${step} FAILED (curl exit ${curl_exit})${NC}"
    exit "${curl_exit}"
  fi

  if [[ "${http_code}" =~ ^2[0-9][0-9]$ ]]; then
    echo -e "${GREEN}${CHECK} ${step} OK (HTTP ${http_code})${NC}"
  else
    echo -e "${RED}${CROSS} ${step} FAILED (HTTP ${http_code})${NC}"
    exit 1
  fi
}

echo -e "${YELLOW}=== Tasks API smoke test against ${BASE_URL} (X-User-Id=${USER_ID}) ===${NC}"

TASKS_URL="${BASE_URL}/api/api/v1/tasks"

echo
echo -e "${INFO} 1) List personal tasks (may be empty)"
curl_check "List tasks" \
  -H "X-User-Id: ${USER_ID}" \
  -H "Accept: application/json" \
  "${TASKS_URL}?page=0&size=20"

echo
echo -e "${INFO} 2) Create a new personal task"

set +e
CREATE_RESPONSE=$(curl -sS -w "\n%{http_code}" \
  -X POST "${TASKS_URL}" \
  -H "X-User-Id: ${USER_ID}" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Prepare math exam",
    "description": "Review chapters 1–5 and solve practice problems",
    "dueDate": "2025-06-30T18:00:00",
    "priority": "HIGH",
    "category": "Mathematics"
  }')
curl_exit=$?
set -e

if [[ "${curl_exit}" -ne 0 ]]; then
  echo -e "${RED}${CROSS} Create task FAILED (curl exit ${curl_exit})${NC}"
  exit "${curl_exit}"
fi

CREATE_BODY=$(printf "%s" "${CREATE_RESPONSE}" | sed '$d')
CREATE_STATUS=$(printf "%s" "${CREATE_RESPONSE}" | tail -n1)

echo "HTTP status: ${CREATE_STATUS}"
echo "Response body:"
echo "${CREATE_BODY}"

if [[ "${CREATE_STATUS}" != "201" && "${CREATE_STATUS}" != "200" ]]; then
  echo -e "${RED}${CROSS} Create task FAILED (HTTP ${CREATE_STATUS})${NC}"
  exit 1
fi

TASK_ID=$(printf "%s" "${CREATE_BODY}" | jq -r '.id')

if [[ -z "${TASK_ID}" || "${TASK_ID}" == "null" ]]; then
  echo -e "${RED}${CROSS} Could not extract task id from create response.${NC}"
  exit 1
fi

echo -e "${GREEN}${CHECK} Created task with id: ${TASK_ID}${NC}"

echo
echo -e "${INFO} 3) List personal tasks again"
curl_check "List tasks after create" \
  -H "X-User-Id: ${USER_ID}" \
  -H "Accept: application/json" \
  "${TASKS_URL}?page=0&size=20"

echo
echo -e "${INFO} 4) Get task by id (${TASK_ID})"
curl_check "Get task by id" \
  -H "Accept: application/json" \
  "${TASKS_URL}/${TASK_ID}"

echo
echo -e "${INFO} 5) Update task via PUT"
curl_check "Update task" \
  -X PUT "${TASKS_URL}/${TASK_ID}" \
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

echo
echo -e "${INFO} 6) Update task status via PATCH"
curl_check "Update task status" \
  -X PATCH "${TASKS_URL}/${TASK_ID}/status" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "COMPLETED"
  }'

echo
echo -e "${INFO} 7) Delete task"
curl_check "Delete task" \
  -X DELETE "${TASKS_URL}/${TASK_ID}"

echo
echo -e "${GREEN}${CHECK} Tasks API smoke test completed successfully.${NC}"

#!/usr/bin/env bash

##
## End-to-end smoke test for the Tasks API of StudyConnect Backend.
##
## This script follows the documentation in docs/api/tasks.md and performs
## a plausible sequence of operations:
##
##  1. List personal tasks for a user
##  2. Create a new personal task
##  3. List tasks again to see the new one
##  4. Fetch the created task by id
##  5. Update the task (PUT)
##  6. Update only the status (PATCH)
##  7. Delete the task
##
## Requirements:
##  - Backend running inside the devcontainer
##  - Default base URL: http://localhost:8080
##  - Test profile typically uses in-memory H2 and may require that
##    a user with id 1 exists in the database.
##

set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"
USER_ID="${USER_ID:-1}"

echo "=== Tasks API smoke test against ${BASE_URL} (X-User-Id=${USER_ID}) ==="

echo
echo "1) List personal tasks (may be empty)"
curl -sS -w "\nHTTP %{http_code}\n" \
  -H "X-User-Id: ${USER_ID}" \
  -H "Accept: application/json" \
  "${BASE_URL}/api/v1/tasks?page=0&size=20"

echo
echo "2) Create a new personal task"
CREATE_RESPONSE=$(curl -sS -w "\n%{http_code}" \
  -X POST "${BASE_URL}/api/v1/tasks" \
  -H "X-User-Id: ${USER_ID}" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Prepare math exam",
    "description": "Review chapters 1–5 and solve practice problems",
    "dueDate": "2025-06-30T18:00:00",
    "priority": "HIGH",
    "category": "Mathematics"
  }')

CREATE_BODY=$(printf "%s" "${CREATE_RESPONSE}" | sed '$d')
CREATE_STATUS=$(printf "%s" "${CREATE_RESPONSE}" | tail -n1)

echo "Response status: ${CREATE_STATUS}"
echo "Response body:"
echo "${CREATE_BODY}"

if [[ "${CREATE_STATUS}" != "201" && "${CREATE_STATUS}" != "200" ]]; then
  echo "Create task failed with status ${CREATE_STATUS}" >&2
  exit 1
fi

TASK_ID=$(printf "%s" "${CREATE_BODY}" | jq -r '.id')

if [[ -z "${TASK_ID}" || "${TASK_ID}" == "null" ]]; then
  echo "Could not extract task id from create response." >&2
  exit 1
fi

echo "Created task with id: ${TASK_ID}"

echo
echo "3) List personal tasks again"
curl -sS -w "\nHTTP %{http_code}\n" \
  -H "X-User-Id: ${USER_ID}" \
  -H "Accept: application/json" \
  "${BASE_URL}/api/v1/tasks?page=0&size=20"

echo
echo "4) Get task by id (${TASK_ID})"
curl -sS -w "\nHTTP %{http_code}\n" \
  -H "Accept: application/json" \
  "${BASE_URL}/api/v1/tasks/${TASK_ID}"

echo
echo "5) Update task via PUT"
curl -sS -w "\nHTTP %{http_code}\n" \
  -X PUT "${BASE_URL}/api/v1/tasks/${TASK_ID}" \
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

echo
echo "6) Update task status via PATCH"
curl -sS -w "\nHTTP %{http_code}\n" \
  -X PATCH "${BASE_URL}/api/v1/tasks/${TASK_ID}/status" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "COMPLETED"
  }'

echo
echo "7) Delete task"
curl -sS -w "\nHTTP %{http_code}\n" \
  -X DELETE "${BASE_URL}/api/v1/tasks/${TASK_ID}"

echo
echo "Tasks API smoke test completed."


