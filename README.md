# OpportuneBackend

A Spring Boot backend application built with Java 21 for the Opportune project.

## Overview

OpportuneBackend is a RESTful API built using Spring Boot 4.0.3 that provides backend services for the Opportune application. The project is designed with security, data persistence, and API documentation in mind.

## Tech Stack

- **Framework**: Spring Boot 4.0.3
- **Language**: Java 21
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Security**: Spring Security
- **ORM**: Spring Data JPA with Hibernate
- **API Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Testing**: JUnit 5, Spring Boot Test

## Project Structure

```
OpportuneBackend/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   └── Application.java          # Spring Boot Application entry point
│   │   └── resources/
│   │       └── application.properties    # Application configuration
│   └── test/
│       └── java/com/example/demo/
│           └── ApplicationTests.java     # Unit tests
├── pom.xml                               # Maven configuration
├── mvnw / mvnw.cmd                       # Maven wrapper scripts
└── README.md                             # This file
```

## Prerequisites

- Java 21 or higher
- Maven 3.6+ or use the included Maven wrapper
- PostgreSQL 12+

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/MalakWaleed00/OpportuneBackend.git
cd OpportuneBackend
```

### 2. Database Setup

Create a PostgreSQL database and user:

```sql
CREATE USER opportune_user WITH PASSWORD 'opportune_pass';
CREATE DATABASE opportune_db OWNER opportune_user;
```

Update the database connection details in `src/main/resources/application.properties` if needed.

### 3. Build the Project

Using Maven wrapper (Unix/Linux/Mac):
```bash
./mvnw clean install
```

Using Maven wrapper (Windows):
```bash
mvnw.cmd clean install
```

Or if Maven is installed globally:
```bash
mvn clean install
```

### 4. Run the Application

Using Maven wrapper:
```bash
./mvnw spring-boot:run
```

Or after building:
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080` by default.

## Configuration

### Database Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/opportune_db
spring.datasource.username=opportune_user
spring.datasource.password=opportune_pass
```

### Hibernate Configuration

- **DDL Auto**: Set to `update` (automatically creates/updates tables)
- **SQL Formatting**: Enabled for better readability
- **Dialect**: PostgreSQL

## API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

The application includes SpringDoc OpenAPI (v3.0.2) for automatic API documentation generation.

## Features

- ✅ Spring Security integration for authentication and authorization
- ✅ JPA/Hibernate ORM for database operations
- ✅ PostgreSQL database support
- ✅ Automatic API documentation with Swagger UI
- ✅ Development tools (Spring DevTools for hot reload)
- ✅ Comprehensive testing setup
- ✅ Maven-based build management

## Testing

Run the test suite:

```bash
./mvnw test
```

Or using Maven directly:
```bash
mvn test
```

The project includes:
- Spring Boot Test
- Spring Security Test
- JPA Test support

## Development

### Hot Reload

Spring DevTools is included for development. The application will automatically restart when files are changed during development.

### IDE Setup

Recommended IDEs:
- IntelliJ IDEA (Community or Ultimate)
- Eclipse IDE
- VS Code with Spring Boot Extension Pack

## Building for Production

Create a production build:

```bash
./mvnw clean package -DskipTests
```

This creates an executable JAR file in the `target/` directory.

## Dependencies

Key dependencies:
- `spring-boot-starter-data-jpa` - Database access with JPA
- `spring-boot-starter-security` - Authentication & authorization
- `spring-boot-starter-webmvc` - Web MVC framework
- `springdoc-openapi-starter-webmvc-ui` - API documentation

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Push to the branch
5. Open a Pull Request

## Support

For issues, questions, or suggestions, please create an issue in the GitHub repository.

## License

This project is part of the Opportune initiative.

---

**Last Updated**: June 2026
