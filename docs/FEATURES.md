# Key Features: IS-Backend

## Functional Overview
The `IS-Backend` provides a comprehensive set of RESTful APIs to manage the university's academic resources and scheduling.

### 1. User Authentication & Security
- **JWT Authentication**: Secure, stateless authentication using JSON Web Tokens (via jjwt library).
- **Role-Based Access Control (RBAC)**: Differentiates between `ADMIN` and `USER` roles to protect sensitive administrative endpoints.
- **Unified Security Filter**: Global request filtering via `JwtAuthenticationFilter` to ensure only authorized requests reach protected endpoints. Public read endpoints (prefixed with `/user/`) are accessible without authentication.

### 2. Resource Management (CRUD)
- **Room Management**: Manage physical classrooms, labs, and lecture halls, including `name`, `capacity`, `type`, and `location`. Unique constraints on name and location prevent duplicates.
- **Professor Profiles**: Maintain records of teaching staff with `firstName`, `lastName`, and `department`, linked to user accounts.
- **Student Groups**: Organize students into cohorts with a unique `identifier`, `specialization`, and `year`.
- **Subject Catalog**: Define the academic curriculum with `name` and `activityType` (e.g., lecture, lab, seminar).

### 3. Scheduling & Coordination
- **Conflict-Free Scheduling**: Application-level logic ensures that groups and professors cannot be double-booked at the same `startingHour`, `scheduleDay`, and `frequency`.
- **Frequency Support**: Schedules support `SAPTAMANAL` (weekly), `PARA` (even weeks), and `INPARA` (odd weeks) frequencies.
- **Advanced Filtering**: Dynamic filtering of schedules by Room, Group, Professor, Subject, day, and frequency via a flexible query API.
- **Time Slot Management**: Handles `startingHour` and `endingHour` for academic sessions across different days of the week.

### 4. Robust Error Handling
- **Global Exception Handling**: Standardized error responses across all API endpoints via `GlobalExceptionHandler`, ensuring a predictable experience for client developers.
- **Custom Exceptions**: `ResourceNotFoundException`, `AlreadyExistsException`, `AccessDeniedException`, `UserUnauthorizedException`.

### 5. API Documentation
- **Swagger UI**: Interactive API documentation available at `/swagger-ui/index.html` via SpringDoc OpenAPI integration.

### 6. Database Migration
- **Flyway**: Versioned SQL migration scripts ensure repeatable and safe schema evolution across environments.
