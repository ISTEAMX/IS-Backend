# Database Design: IS-Backend

## Overview
The `IS-Backend` uses a relational database schema (PostgreSQL) to manage university entities and their relationships. Schema management is handled by **Flyway** migrations located in `src/main/resources/db/migration/`.

## Entity-Relationship Diagram (ERD)
```mermaid
erDiagram
    USER ||--o| PROFESSOR : "optional profile"
    ROOM ||--o{ SCHEDULE : "hosts"
    PROFESSOR ||--o{ SCHEDULE : "teaches"
    SUBJECT ||--o{ SCHEDULE : "covered in"
    SCHEDULE }o--o{ GROUP : "attends (via schedule_groups)"

    USER {
        bigint id PK
        string first_name
        string last_name
        string email UK
        string password
        string role
        boolean password_changed
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
        int semester
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
        bigint subject_id FK
    }

    SCHEDULE_GROUPS {
        bigint schedule_id FK_PK
        bigint group_id FK_PK
    }
```

## Table Descriptions

### User & Professor
- **users**: Stores authentication credentials (`email`, `password`) and role-based permissions (`ADMIN`, `PROFESSOR`). The `password_changed` flag tracks whether a user has changed their default password. Fields: `id`, `first_name`, `last_name`, `email` (unique), `password`, `role`, `password_changed`.
- **professors**: Profile details linked to a specific user account via `user_id` (unique, cascade delete). Fields: `id`, `first_name`, `last_name`, `department`, `user_id`.

### Infrastructure & Resources
- **rooms**: Physical classrooms or facilities. Fields: `id`, `name` (unique), `capacity`, `type`, `location` (unique).
- **student_groups**: Student cohorts organized by specialization, year, and semester. Fields: `id`, `identifier` (unique), `specialization`, `year`, `semester`.
- **subjects**: Academic courses. Fields: `id`, `name`, `activity_type`.

### Scheduling (Coordination)
- **schedules**: The central coordinating table that links a day/time slot to a Room, Professor, and Subject. Fields: `id`, `schedule_day`, `starting_hour`, `ending_hour`, `frequency` (enum: `SAPTAMANAL`, `PARA`, `INPARA`), `professor_id`, `room_id`, `subject_id`.
- **schedule_groups**: Join table for the ManyToMany relationship between schedules and student groups. A single schedule entry can be associated with multiple groups. Composite primary key: (`schedule_id`, `group_id`).

## Migration History

| Version | File | Description |
|---|---|---|
| V1 | `V1__Initial_Schema.sql` | Initial schema: users, professors, student_groups, rooms, subjects, schedules |
| V2 | `V2__Seed_Data.sql` | Seed data: faculty users, professors across 4 departments |
| V3 | `V3__Add_Password_Changed_Flag.sql` | Add `password_changed` boolean to users |
| V4 | `V4__Add_Semester_To_Groups.sql` | Add `semester` column to student_groups |
| V5 | `V5__Seed_Subjects_And_Groups_CII_Sem2.sql` | Seed groups, rooms, and subjects for all 10 specializations |
| V6 | `V6__Schedule_Multiple_Groups.sql` | Convert schedules→groups from OneToMany to ManyToMany via `schedule_groups` join table |

## Data Persistence Strategy
- **Hibernate**: Used for ORM (Object-Relational Mapping).
- **DDL Management**: `spring.jpa.hibernate.ddl-auto` is set to `validate` — Hibernate validates the schema against entity mappings but does **not** modify it.
- **Flyway Migrations**: All schema changes are managed through versioned SQL migration files in `src/main/resources/db/migration/`. The initial schema is defined in `V1__Initial_Schema.sql`.
