# StudyConnect Backend

A Spring Boot backend application for the StudyConnect student productivity tool.

## Features

- **Health Check Endpoints**: Basic health monitoring endpoints
- **Spring Boot 3.4.0**: Latest Spring Boot version with Java 25 support
- **JUnit 5 Testing**: Comprehensive test suite with JUnit Jupiter
- **H2 Database**: In-memory database for testing
- **PostgreSQL Support**: Production database configuration
- **JWT Authentication**: Ready for JWT-based authentication
- **RESTful API**: Clean API design following REST principles

## Technology Stack

- **Java**: OpenJDK 25
- **Spring Boot**: 3.4.0
- **Maven**: 3.x
- **JUnit**: 5 (Jupiter)
- **Database**: PostgreSQL (production), H2 (testing)
- **Security**: Spring Security with JWT support

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/studyconnect/backend/
│   │   │   ├── StudyConnectBackendApplication.java
│   │   │   └── controller/
│   │   │       └── HealthController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/com/studyconnect/backend/
│       │   ├── StudyConnectBackendApplicationTests.java
│       │   └── controller/
│       │       └── HealthControllerTest.java
│       └── resources/
│           └── application-test.properties
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites

- Java 25 (OpenJDK 25)
- Maven 3.x

### Running the Application

1. **Compile the project:**
   ```bash
   mvn clean compile
   ```

2. **Run tests:**
   ```bash
   mvn test
   ```

3. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Test the health endpoint:**
   ```bash
   curl http://localhost:8080/api/health
   ```

### API Endpoints

- `GET /api/health` - Application health status
- `GET /api/health/ping` - Simple ping endpoint

## Configuration

### Application Properties

The application uses different configurations for different environments:

- **Main**: `src/main/resources/application.properties`
- **Test**: `src/test/resources/application-test.properties`

### Database Configuration

- **Production**: PostgreSQL (configured in main properties)
- **Testing**: H2 in-memory database (configured in test properties)

## Testing

The project includes comprehensive tests:

- **Integration Tests**: Full Spring Boot context loading
- **Unit Tests**: Individual component testing
- **Test Coverage**: All critical components are tested

Run tests with:
```bash
mvn test
```

## Development

### Adding New Features

1. Create new controllers in the `controller` package
2. Add corresponding tests in the `test` package
3. Follow the existing naming conventions
4. Ensure all tests pass before committing

### Code Quality

- Follow Java naming conventions
- Include comprehensive JavaDoc comments
- Write unit tests for all new functionality
- Ensure code compiles without warnings

## License

This project is part of the StudyConnect application suite.
