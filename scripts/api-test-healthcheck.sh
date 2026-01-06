#!/usr/bin/env bash

##
## Healthcheck script for StudyConnect Backend
##
## - Assumes the backend is running inside the devcontainer
## - Default base URL: http://localhost:8080/api
##

set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080/api}"

# ANSI colors and symbols
GREEN="\033[0;32m"
RED="\033[0;31m"
YELLOW="\033[0;33m"
NC="\033[0m" # No Color

CHECK="✔"
CROSS="✖"
INFO="➜"

check_endpoint() {
  local name="$1"
  local url="$2"

  echo
  echo -e "${INFO} Checking ${name} (${url})"

  local curl_exit=0
  local http_code

  # Temporarily disable -e to capture curl exit status ourselves
  set +e
  http_code=$(curl -sS -o /dev/null -w "%{http_code}" "${url}")
  curl_exit=$?
  set -e

  if [[ "${curl_exit}" -ne 0 ]]; then
    echo -e "${RED}${CROSS} ${name} FAILED (curl exit ${curl_exit})${NC}"
    exit "${curl_exit}"
  fi

  if [[ "${http_code}" == "200" ]]; then
    echo -e "${GREEN}${CHECK} ${name} OK (HTTP ${http_code})${NC}"
  else
    echo -e "${RED}${CROSS} ${name} FAILED (HTTP ${http_code})${NC}"
    exit 1
  fi
}

echo -e "${YELLOW}=== Healthcheck against ${BASE_URL} ===${NC}"

check_endpoint "health" "${BASE_URL}/health"
check_endpoint "health ping" "${BASE_URL}/health/ping"

echo
echo -e "${GREEN}${CHECK} Healthcheck completed successfully.${NC}"
