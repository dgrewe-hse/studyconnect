#!/bin/bash

# Script to run Spring Boot backend with test profile (H2 database)
# This is useful for E2E testing without requiring PostgreSQL

echo "Starting StudyConnect Backend with test profile (H2 database)..."
echo ""
echo "This will:"
echo "  - Use H2 in-memory database (no PostgreSQL required)"
echo "  - Run on http://localhost:8080/api"
echo "  - Initialize test users (user1@example.com, user2@example.com)"
echo ""
echo "Press Ctrl+C to stop the server"
echo ""

cd "$(dirname "$0")"

# Unset environment variables that might override test profile settings
# These are typically set by docker-compose.yml
unset SPRING_DATASOURCE_URL
unset SPRING_DATASOURCE_USERNAME
unset SPRING_DATASOURCE_PASSWORD
unset SPRING_DATASOURCE_DRIVER_CLASS_NAME

# Run with test profile and explicitly override datasource properties
./mvnw spring-boot:run \
  -Dspring-boot.run.profiles=test \
  -Dspring-boot.run.arguments="--spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=PostgreSQL --spring.datasource.driver-class-name=org.h2.Driver --spring.datasource.username=sa --spring.datasource.password="

