# User Service

Core identity and user management service for the hospital_app system. It exposes a REST API for user CRUD and authentication (JWT issuance), and a gRPC API (with mTLS) consumed by other services (e.g., Appointment Service) to validate users and fetch user details.


## Tech stack
- Java 21, Spring Boot 3.5
- Spring Web, Validation, Security (JWT resource server via shared jwt_security_common module)
- Spring Data JPA (PostgreSQL)
- springdoc-openapi (Swagger UI)
- gRPC server (net.devh) with TLS/mTLS
- BouncyCastle (TLS utils) 


## Architecture highlights
- Hexagonal architecture (ports/adapters) with clear separation of concerns.
- Inbound adapters:
  - REST controllers for User CRUD (/user), login (/login), and user existence checks (/doctor/{id}/exists, /patient/{id}/exists).
  - gRPC controller implementing UserService (GetUser, doctorExists, patientExists) used by other services.
- Outbound adapters:
  - JPA repositories for persistence (users table).
- Security:
  - REST: JWT resource server. /login is public; /user/** requires ADMIN; existence endpoints require authentication.
  - gRPC: TLS with client authentication (mTLS) configurable.


## Running locally
There are two common ways to run this service.

1) Using Docker Compose (recommended to run the whole system)
- Prerequisites: Docker and Docker Compose
- From the repo root, start DB and this service:
  docker compose up -d user-service user-service-db
- HTTP base URL: http://localhost:8081
- Swagger UI: http://localhost:8081/swagger-ui/index.html
- gRPC server: localhost:9090 (TLS/mTLS)
- PostgreSQL (service DB): localhost:5437 (mapped to container 5432)

2) Running via Maven locally
- Prerequisites: JDK 21, Maven, PostgreSQL, and TLS certs for gRPC server in classpath (see Environment variables).
- Export env vars as in the root .env or create application-local.yml.
- Build monorepo then run only user_service:
  mvn -q -DskipTests package
  mvn -q -pl user_service -am spring-boot:run
- Service runs on port 8080 by default; in Docker it is mapped to 8081. gRPC runs on 9090.

Remote debugging
- If ENABLE_REMOTE_DEBUG=true in Docker, Java debug port 5005 is exposed.


## REST API
Base paths:
- /login
- /user
- /doctor
- /patient

Authentication and authorization:
- JWT required for most endpoints; /login is public.
- /user/** requires role ADMIN (see UserSecurityConfiguration).
- /doctor/{id}/exists and /patient/{id}/exists require any authenticated user.

Endpoints overview:
- POST /login
  - Description: Authenticate user and return a JWT
  - Body example:
    {
      "user": "admin@hospital.local",  // or username
      "password": "your_password"
    }
  - Response example:
    { "token": "<JWT>" }

- GET /user?pageNumber=0&pageSize=10 (ADMIN)
  - Description: Paginated list of users
- POST /user (ADMIN)
  - Description: Create a new user
- GET /user/{id} (ADMIN)
  - Description: Get user by ID
- PUT /user/{id} (ADMIN)
  - Description: Update user fields
- PATCH /user/{id}?enabled=true|false (ADMIN)
  - Description: Enable/disable a user
- PATCH /user/{id}/change-password (ADMIN)
  - Description: Change user password
  - Body example:
    {
      "oldPassword": "old",
      "newPassword": "newStrongPassword!"
    }

- GET /doctor/{id}/exists (authenticated)
  - Description: Check whether the given UUID belongs to an existing doctor
- GET /patient/{id}/exists (authenticated)
  - Description: Check whether the given UUID belongs to an existing patient

OpenAPI/Swagger
- Swagger UI: /swagger-ui/index.html
- OpenAPI JSON: /v3/api-docs


## gRPC API (TLS/mTLS)
- Server port: 9090
- Service: UserService
- Methods:
  - GetUser(GetUserRequest) -> GetUserResponse
  - doctorExists(UserExistsRequest) -> UserExistsResponse
  - patientExists(UserExistsRequest) -> UserExistsResponse
- TLS/mTLS certificates are loaded from classpath and can require client authentication depending on env var HOSPITAL_APP_USER_SERVICE_CLIENT_AUTH.
- Implementation: infra/adapter/in/grpc/controller/user/UserGrpcController.java


## Persistence
- PostgreSQL via Spring Data JPA
- Entity: infra/adapter/out/db/jpa/user/JpaUserEntity (table: users)
- Tracks basic attributes (name, username, email, cpf, passwordHash, role, timestamps, enabled)
- Schema is controlled by HOSPITAL_APP_USER_SERVICE_DB_DDL_AUTO (default update in .env)


## Environment variables
Consumed by user_service (see application.yml and docker-compose.yml). Defaults are provided by the root .env.

Database
- HOSPITAL_APP_USER_SERVICE_DB_URL=jdbc:postgresql://user-service-db:5432/user_service_db
- HOSPITAL_APP_USER_SERVICE_DB_USER=user
- HOSPITAL_APP_USER_SERVICE_DB_PASS=pass
- HOSPITAL_APP_USER_SERVICE_DB_DRIVER=org.postgresql.Driver
- HOSPITAL_APP_USER_SERVICE_DB_DDL_AUTO=update
- HOSPITAL_APP_USER_SERVICE_DB_DIALECT=org.hibernate.dialect.PostgreSQLDialect

gRPC TLS
- HOSPITAL_APP_USER_SERVICE_CRT=classpath:tls/user_service.crt
- HOSPITAL_APP_USER_SERVICE_KEY=classpath:tls/user_service_pkcs8.key
- HOSPITAL_APP_USER_SERVICE_CA=classpath:tls/ca.crt
- HOSPITAL_APP_USER_SERVICE_CLIENT_AUTH=REQUIRE  # or NONE

Other
- ENABLE_REMOTE_DEBUG=true (when running in Docker, exposes port 5005)


## Useful commands
- Build monorepo: mvn -q -DskipTests package
- Run only user service: mvn -q -pl user_service -am spring-boot:run
- Start with DB via Docker:
  docker compose up -d user-service user-service-db
- View logs: docker logs -f hospital-app-user-service


## Troubleshooting
- gRPC TLS errors: ensure cert files exist under user_service/src/main/resources/tls and env vars point to the correct classpath locations.
- DB connection failures: confirm user-service-db container is healthy and env vars are correct (see .env).
- 401/403 on REST: verify JWT token and roles; /user/** requires ADMIN.
- Swagger not loading: check that the service is on http://localhost:8081 and that springdoc is included.


## Project paths and references
- Dockerfile: user_service/Dockerfile
- Spring config: user_service/src/main/resources/application.yml
- TLS files: user_service/src/main/resources/tls/
- Security: infra/config/security/UserSecurityConfiguration.java
- REST controllers:
  - infra/adapter/in/rest/controller/user/UserRestController.java
  - infra/adapter/in/rest/controller/login/UserLoginRestController.java
  - infra/adapter/in/rest/controller/doctor/DoctorRestController.java
  - infra/adapter/in/rest/controller/patient/PatientRestController.java
- gRPC controller: infra/adapter/in/grpc/controller/user/UserGrpcController.java
- Persistence entity: infra/adapter/out/db/jpa/user/JpaUserEntity.java


## License
This project is part of an educational/portfolio repository. See root-level LICENSE if available.