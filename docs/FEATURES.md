# Key Features: IS-Backend

## Functional Overview
The `IS-Backend` provides a comprehensive set of RESTful APIs to manage the university's academic resources and scheduling.

### 1. User Authentication & Security
- **JWT Authentication**: Secure, stateless authentication using JSON Web Tokens.
- **Role-Based Access Control (RBAC)**: Differentiates between `ADMIN` and `USER` roles to protect sensitive administrative endpoints.
- **Unified Security Filter**: Global request filtering to ensure only authorized requests reach the application logic.

### 2. Resource Management (CRUD)
- **Room Management**: Manage physical classrooms, labs, and lecture halls, including capacity and location.
- **Professor Profiles**: Maintain records of teaching staff, their specializations, and availability.
- **Student Groups**: Organize students into cohorts for streamlined scheduling.
- **Subject Catalog**: Define the academic curriculum with specific subject IDs and names.

### 3. Scheduling & Coordination
- **Conflict-Free Scheduling**: The system logic ensures that time slots, rooms, and professors are coordinated to avoid scheduling overlaps.
- **Advanced Filtering**: Support for filtering schedules by Room, Group, or Professor, providing flexible views of the university timetable.
- **Automated Time Slot Management**: Handles start and end times for academic sessions across different days of the week.

### 4. Robust Error Handling
- **Global Exception Handling**: Standardized error responses across all API endpoints, ensuring a predictable experience for client developers.
- **Data Validation**: Server-side validation of incoming DTOs to maintain data integrity.
