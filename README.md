# OPA Policy Spring Boot Application

This application demonstrates a secure Spring Boot application that integrates with Open Policy Agent (OPA) for fine-grained authorization control. It features JWT-based authentication and role-based access control using OPA policies.
Read the accompanying blog to better understand how the project works: https://blog.arunbalachandran.com/building-a-policy-engine-using-opa-open-policy-agent

## Features

- JWT-based authentication with access and refresh tokens
- OPA policy integration for fine-grained authorization
- Role-based access control
- Stateless session management
- RESTful API endpoints
- PostgreSQL database integration
- Redis for caching
- Docker Compose setup for easy deployment

## Architecture

The application follows a layered architecture with the following components:

### Core Components

1. **Security Layer**
   - `JWTAuthenticationFilter`: Handles JWT token validation and authentication
   - `JWTService`: Manages JWT token generation, validation, and claims extraction
   - `SecurityConfiguration`: Configures Spring Security with JWT and OPA integration
   - `CustomFilterExceptionHandler`: Handles security-related exceptions

2. **Authorization Layer**
   - `OpaService`: Communicates with the OPA server for policy decisions
   - `CustomAuthorizationManager`: Implements custom authorization logic
   - `MethodSecurityConfig`: Configures method-level security with OPA integration

3. **Data Layer**
   - PostgreSQL database for persistent storage
   - Redis for caching
   - Repository interfaces for data access

4. **API Layer**
   - REST controllers for user management and authentication
   - DTOs for data transfer
   - Exception handling

## Docker Compose Setup

The application uses Docker Compose to orchestrate the following services:

```yaml
services:
  opa:
    image: "openpolicyagent/opa:latest"
    ports:
      - "8181:8181"
    command: run --server --log-level debug --addr=0.0.0.0:8181

  postgres:
    image: "postgres:14.17"
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
      POSTGRES_DB: opademo
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  redis:
    image: "redis:latest"
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
```

## Configuration

### Application Properties

```yaml
spring:
  application:
    name: opapolicyspringboot
  jpa:
    hibernate:
      ddl-auto: validate
    database-platform: org.hibernate.dialect.PostgreSQLDialect
  data:
    redis:
      host: localhost
      port: 6379
      password: password
  datasource:
    url: "jdbc:postgresql://localhost:5432/opademo?currentSchema=opademo"
    username: postgres
    password: password

opa.url: http://localhost:8181
```

### Security Configuration

- JWT secret key and token expiration times are configurable
- CORS is configured to allow requests from `http://localhost:4200`
- CSRF is disabled for API endpoints
- Stateless session management is enabled

## API Endpoints

### Authentication
- `POST /api/v1/auth/login`: Authenticate and receive JWT tokens
- `POST /api/v1/auth/refresh`: Refresh access token using refresh token
- `POST /api/v1/auth/logout`: Invalidate tokens

### User Management
- `GET /api/v1/users`: Get all users (protected by OPA policy)
- `POST /api/v1/signup`: Register new user

## OPA Integration

The application uses OPA for fine-grained authorization. The OPA service:
1. Receives authorization requests with user roles and requested actions
2. Evaluates policies against the input
3. Returns allow/deny decisions

Example OPA policy usage in the code:
```java
@PreAuthorize("hasAuthorization(#authToken)")
@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<UserDTO>> findAll(
    @RequestHeader(Constants.AUTHORIZATION_HEADER) String authToken
) {
    // Implementation
}
```

## Getting Started

1. Clone the repository
2. Start the services using Docker Compose:
   ```bash
   docker-compose up -d
   ```
3. Run the Spring Boot application
4. Access the application at `http://localhost:8080`

## Dependencies

- Spring Boot 2.7.0
- Spring Security
- Spring Data JPA
- Spring Data Redis
- JJWT for JWT handling
- PostgreSQL JDBC Driver
- Open Policy Agent
- Lombok
- Spring Web

## Security Considerations

- JWT tokens are signed using a secure secret key
- Password hashing using BCrypt
- CORS is configured for specific origins
- OPA policies provide fine-grained access control
- Exception handling for security-related issues
- Stateless session management

## License

This project is licensed under the MIT License - see the LICENSE file for details.
