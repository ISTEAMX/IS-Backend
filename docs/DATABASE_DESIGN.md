# Database Design: IS-Backend

## Overview
The `IS-Backend` uses a relational database schema (PostgreSQL) to manage university entities and their relationships. Schema management is handled by **Flyway** migrations located in `src/main/resources/db/migration/`.

## Entity-Relationship Diagram (ERD)
```mermaid
erDiagram
    USER ||--o| PROFESSOR : "optional profile"
    ROOM ||--o{ SCHEDULE : "hosts"
    PROFESSOR ||--o{ SCHEDULE : "teaches"
    GROUP ||--o{ SCHEDULE : "attends"
    SUBJECT ||--o{ SCHEDULE : "covered in"

    USER {
        bigint id PK
        string first_name
        string last_name
        string email UK
        string password
        string role
    }

    PROFESSOR {
        bigint id PK
        string first_name
        string last_name
        string department
        bigint user_id FK_UK
    }

    ROOM {
        bigint id PK
        string name UK
        int capacity
        string type
        string location UK
    }

    GROUP {
        bigint id PK
        string identifier UK
        string specialization
        int year
    }

    SUBJECT {
        bigint id PK
        string name
        string activity_type
    }

    SCHEDULE {
        bigint id PK
        string schedule_day
        string starting_hour
        string ending_hour
        string frequency
        bigint professor_id FK
        bigint room_id FK
        bigint group_id FK
        bigint subject_id FK
    }
```

## Table Descriptions

### User & Professor
- **users**: Stores authentication credentials (`email`, `password`) and role-based permissions (`ADMIN`, `USER`). Fields: `id`, `first_name`, `last_name`, `email` (unique), `password`, `role`.
- **professors**: Profile details linked to a specific user account via `user_id` (unique, cascade delete). Fields: `id`, `first_name`, `last_name`, `department`, `user_id`.

### Infrastructure & Resources
- **rooms**: Physical classrooms or facilities. Fields: `id`, `name` (unique), `capacity`, `type`, `location` (unique).
- **student_groups**: Student cohorts organized by specialization and year. Fields: `id`, `identifier` (unique), `specialization`, `year`.
- **subjects**: Academic courses. Fields: `id`, `name`, `activity_type`.

### Scheduling (Coordination)
- **schedules**: The central coordinating table that links a day/time slot to a Room, Professor, Group, and Subject. Fields: `id`, `schedule_day`, `starting_hour`, `ending_hour`, `frequency` (enum: `SAPTAMANAL`, `PARA`, `INPARA`), `professor_id`, `room_id`, `group_id`, `subject_id`.

## Data Persistence Strategy
- **Hibernate**: Used for ORM (Object-Relational Mapping).
- **DDL Management**: `spring.jpa.hibernate.ddl-auto` is set to `validate` — Hibernate validates the schema against entity mappings but does **not** modify it.
- **Flyway Migrations**: All schema changes are managed through versioned SQL migration files in `src/main/resources/db/migration/`. The initial schema is defined in `V1__Initial_Schema.sql`.
