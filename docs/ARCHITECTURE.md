# Architecture: IS-Backend

## High-level Overview
The `IS-Backend` is a Spring Boot application built using a **Layered Architecture**. This ensures a clear separation of concerns, improves maintainability, and facilitates testing.

## Technical Stack
- **Framework**: [Spring Boot 4.0+](https://spring.io/projects/spring-boot)
- **Language**: [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)
- **Database**: [PostgreSQL 15+](https://www.postgresql.org/)
- **Persistence**: [Spring Data JPA (Hibernate)](https://spring.io/projects/spring-data-jpa)
- **Security**: [Spring Security + JWT (jjwt)](https://spring.io/projects/spring-security)
- **Build Tool**: [Maven 3.9+](https://maven.apache.org/)
- **Migration**: [Flyway](https://flywaydb.org/) for database schema versioning
- **API Docs**: [SpringDoc OpenAPI (Swagger UI)](https://springdoc.org/)
- **Utilities**: [Lombok](https://projectlombok.org/)

## Core Architectural Layers

### Component Interaction (Request Flow)
The application follows a standard request-response cycle through its layers:
1.  **Controller Layer**: Handles incoming HTTP requests, validates DTOs, and returns response objects.
2.  **Service Layer**: Implements business logic and orchestrates data flow between repositories and mappers.
3.  **Repository Layer**: Interacts with the PostgreSQL database via Spring Data JPA.
4.  **Data Layer**: Persists academic entities in a relational schema managed by Flyway migrations.

### Layer Diagram
```mermaid
graph TD
    subgraph "Client Layer"
        Web[IS-Frontend / Browser]
        Mobile[Mobile Apps]
    end

    subgraph "Application Layer (Spring Boot)"
        Controller[REST Controllers]
        Security[Spring Security / JWT Filter]
        Service[Service Layer - Business Logic]
        Mapper[DTO Mappers]
    end

    subgraph "Data Layer"
        Repo[Spring Data JPA Repositories]
        Flyway[Flyway Migrations]
        DB[(PostgreSQL)]
    end

    Web --> Security
    Mobile --> Security
    Security --> Controller
    Controller --> Service
    Service --> Repo
    Repo --> DB
    Flyway --> DB
    Service --> Mapper
```

## Key Components
- **Controllers**: Located in `src/main/java/com/isteamx/university/controller/` — `UserController`, `RoomController`, `ScheduleController`, `GroupController`, `SubjectController`, `ProfessorController`.
- **Services**: Located in `src/main/java/com/isteamx/university/service/` (interfaces) and `service/impl/` (implementations).
- **Repositories**: Located in `src/main/java/com/isteamx/university/repository/`.
- **Entities**: Located in `src/main/java/com/isteamx/university/entity/` — `User`, `Professor`, `Room`, `Group`, `Subject`, `Schedule`.
- **DTOs**: Data Transfer Objects in `src/main/java/com/isteamx/university/dto/` to decouple internal entities from the external API representation.
- **DTO Mappers**: Located in `src/main/java/com/isteamx/university/dtoMapper/` for converting between Entities and DTOs.
- **Configuration**: Located in `src/main/java/com/isteamx/university/configuration/` — `SecurityConfig`, `JwtAuthenticationFilter`, `ApplicationConfig`.
- **OpenAPI Config**: Located in `src/main/java/com/isteamx/university/config/OpenApiConfig.java`.
- **Enums**: Located in `src/main/java/com/isteamx/university/enums/` — `Frequency` (SAPTAMANAL, PARA, INPARA).
- **Exceptions**: Located in `src/main/java/com/isteamx/university/exception/` — `GlobalExceptionHandler` and custom exception classes.
- **Utilities**: Located in `src/main/java/com/isteamx/university/util/` — `JwtUtil`.
