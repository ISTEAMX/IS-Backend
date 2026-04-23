# API Documentation: IS-Backend

## Overview
The `IS-Backend` provides a RESTful API designed for managing university resources. All endpoints are prefixed with `/api`.

## Authentication (`/user`)
| Method | Path | Description | Request Body | Response |
|--------|------|-------------|--------------|----------|
| POST | `/register` | Register a new user | `UserDTO` | `{"message": "User registered successfully"}` |
| POST | `/login` | Authenticate and get JWT | `LoginDTO` | `ResponseLoginDTO` |

### Security Mechanism
1.  **Request Login**: User sends credentials via POST `/api/user/login`.
2.  **Generate Token**: Server validates and returns a signed JWT.
3.  **Authorized Requests**: Include the JWT in the `Authorization: Bearer <token>` header.
4.  **Filter**: `JwtAuthenticationFilter` intercepts requests to verify the token.

## Resource Management

### Room Management (`/room`)
| Method | Path | Description | Roles Required |
|--------|------|-------------|----------------|
| GET | `/user/{id}` | Get room details by ID | Public/User |
| GET | `/user/rooms` | List all available rooms | Public/User |
| POST | `/create` | Create a new room | Authenticated |
| PUT | `/update` | Update existing room | Authenticated |
| DELETE | `/delete/{id}` | Remove a room | Authenticated |

### Schedule Management (`/schedule`)
| Method | Path | Description | Roles Required |
|--------|------|-------------|----------------|
| GET | `/user/{id}` | Get specific schedule entry | Public/User |
| GET | `/user` | List all schedules | Public/User |
| POST | `/add` | Add new schedule entry | Authenticated |
| PUT | `/update` | Modify schedule entry | Authenticated |
| DELETE | `/delete/{id}` | Remove schedule entry | Authenticated |
| GET | `/user/filter` | Filter schedules dynamically | Public/User |

#### Filter Parameters (`FilterDTO`)
The `/user/filter` endpoint accepts the following optional query parameters:
- `professorId` — Filter by professor
- `roomId` — Filter by room
- `groupId` — Filter by student group
- `subjectId` — Filter by subject
- `scheduleDay` — Filter by day of the week
- `frequency` — Filter by frequency (`SAPTAMANAL`, `PARA`, `INPARA`)

### Group Management (`/group`)
| Method | Path | Description | Roles Required |
|--------|------|-------------|----------------|
| GET | `/user/{id}` | Get group details by ID | Public/User |
| GET | `/user/groups` | List all groups | Public/User |
| POST | `/create` | Create a new group | Authenticated |
| PUT | `/update` | Update existing group | Authenticated |
| DELETE | `/delete/{id}` | Remove a group | Authenticated |

### Subject Management (`/subject`)
| Method | Path | Description | Roles Required |
|--------|------|-------------|----------------|
| GET | `/user/{id}` | Get subject details by ID | Public/User |
| GET | `/user` | List all subjects | Public/User |
| POST | `/create` | Create a new subject | Authenticated |
| PUT | `/update` | Update existing subject | Authenticated |
| DELETE | `/delete/{id}` | Remove a subject | Authenticated |

### Professor Management (`/professor`)
| Method | Path | Description | Roles Required |
|--------|------|-------------|----------------|
| GET | `/user/{id}` | Get professor details by ID | Public/User |
| GET | `/user` | List all professors | Public/User |
| PUT | `/` | Update professor profile | Authenticated |
| DELETE | `/{id}` | Remove a professor | Authenticated |

## Error Responses
The system uses a centralized `GlobalExceptionHandler` to ensure consistent error responses.

| Exception | HTTP Status | Meaning |
|-----------|-------------|---------|
| `ResourceNotFoundException` | 404 Not Found | Entity does not exist. |
| `AlreadyExistsException` | 409 Conflict | Violation of unique constraints (e.g., email, room name). |
| `AccessDeniedException` | 403 Forbidden | User lacks necessary permissions. |
| `UserUnauthorizedException` | 401 Unauthorized | Missing or invalid JWT token. |
| `UsernameNotFoundException` | 401 Unauthorized | User not found during authentication. |

## Data Formats
- **Content-Type**: `application/json`
- **Request/Response Objects**: Documented in `src/main/java/com/isteamx/university/dto/`.

### Key DTOs
- **`UserDTO`**: `id`, `firstName`, `lastName`, `email`, `password`, `role`, `professor`
- **`LoginDTO`**: `email`, `password`
- **`ResponseLoginDTO`**: `token`, `userData` (containing `id`, `professorId`, `firstName`, `lastName`, `role`)
- **`RoomDTO`**: `id`, `name`, `capacity`, `type`, `location`
- **`GroupDTO`**: `id`, `identifier`, `specialization`, `year`
- **`SubjectDTO`**: `id`, `name`, `activityType`
- **`ProfessorDTO`**: `id`, `firstName`, `lastName`, `department`
- **`ScheduleDTO`**: `id`, `scheduleDay`, `startingHour`, `endingHour`, `frequency`, `professorDTO`, `roomDTO`, `groupDTO`, `subjectDTO`
- **`CreateScheduleRequestDTO`**: `id`, `scheduleDay`, `startingHour`, `endingHour`, `frequency`, `professorId`, `subjectId`, `groupId`, `roomId`

## Interactive API Docs
Swagger UI is available at `/swagger-ui/index.html` when the application is running.
