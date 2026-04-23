# API Documentation: IS-Backend

## Overview
The `IS-Backend` provides a RESTful API designed for managing university resources. All endpoints are prefixed with `/api`. Interactive API documentation is available via Swagger UI at `/swagger-ui/` when the application is running.

## Authentication (`/api/user`)
| Method | Path | Description | Request Body | Response |
|--------|------|-------------|--------------|----------|
| POST | `/api/user/register` | Register a new user | `UserDTO` (firstName, lastName, email, password, role, professor) | `{"message": "User registered successfully"}` |
| POST | `/api/user/login` | Authenticate and get JWT | `LoginDTO` (email, password) | `ResponseLoginDTO` (token, userData) |

### Security Mechanism
1.  **Request Login**: User sends credentials via POST `/api/user/login`.
2.  **Generate Token**: Server validates and returns a signed JWT.
3.  **Authorized Requests**: Include the JWT in the `Authorization: Bearer <token>` header.
4.  **Filter**: `JwtAuthenticationFilter` intercepts requests to verify the token.

### Public vs. Protected Endpoints
Endpoints under `/user/**` sub-paths (e.g., `/api/room/user/rooms`) are **publicly accessible**. All other endpoints (create, update, delete) require authentication via JWT.

## Resource Management

### Group Management (`/api/group`)
| Method | Path | Description | Auth Required |
|--------|------|-------------|---------------|
| GET | `/api/group/user/{id}` | Get group by ID | No |
| GET | `/api/group/user/groups` | List all groups | No |
| POST | `/api/group/create` | Create a new group | Yes |
| PUT | `/api/group/update` | Update existing group | Yes |
| DELETE | `/api/group/delete/{id}` | Remove a group | Yes |

### Professor Management (`/api/professor`)
| Method | Path | Description | Auth Required |
|--------|------|-------------|---------------|
| GET | `/api/professor/user/{id}` | Get professor by ID | No |
| GET | `/api/professor/user` | List all professors | No |
| PUT | `/api/professor` | Update professor details | Yes |
| DELETE | `/api/professor/{id}` | Remove a professor | Yes |

### Room Management (`/api/room`)
| Method | Path | Description | Auth Required |
|--------|------|-------------|---------------|
| GET | `/api/room/user/{id}` | Get room details by ID | No |
| GET | `/api/room/user/rooms` | List all available rooms | No |
| POST | `/api/room/create` | Create a new room | Yes |
| PUT | `/api/room/update` | Update existing room | Yes |
| DELETE | `/api/room/delete/{id}` | Remove a room | Yes |

### Subject Management (`/api/subject`)
| Method | Path | Description | Auth Required |
|--------|------|-------------|---------------|
| GET | `/api/subject/user/{id}` | Get subject by ID | No |
| GET | `/api/subject/user` | List all subjects | No |
| POST | `/api/subject/create` | Create a new subject | Yes |
| PUT | `/api/subject/update` | Update existing subject | Yes |
| DELETE | `/api/subject/delete/{id}` | Remove a subject | Yes |

### Schedule Management (`/api/schedule`)
| Method | Path | Description | Auth Required |
|--------|------|-------------|---------------|
| GET | `/api/schedule/user/{id}` | Get specific schedule entry | No |
| GET | `/api/schedule/user` | List all schedules | No |
| POST | `/api/schedule/add` | Add new schedule entry | Yes |
| PUT | `/api/schedule/update` | Modify schedule entry | Yes |
| DELETE | `/api/schedule/delete/{id}` | Remove schedule entry | Yes |
| GET | `/api/schedule/user/filter` | Filter by professor, room, group, subject, day, frequency | No |

### Monitoring (`/api/monitoring`)
| Method | Path | Description | Auth Required |
|--------|------|-------------|---------------|
| POST | `/api/monitoring/error` | Report a frontend error to CloudWatch | No |

**Request body** for `/api/monitoring/error`:
```json
{
  "message": "Error description",
  "stack": "Stack trace (optional)",
  "source": "frontend",
  "url": "https://example.com/page"
}
```

## Error Responses
The system uses a centralized `GlobalExceptionHandler` to ensure consistent error responses.

| Exception | HTTP Status | Meaning |
|-----------|-------------|---------|
| `ResourceNotFoundException` | 404 Not Found | Entity does not exist. |
| `AlreadyExistsException` | 409 Conflict | Violation of unique constraints (e.g., email). |
| `AccessDeniedException` | 403 Forbidden | User lacks necessary permissions. |
| `UserUnauthorizedException` | 401 Unauthorized | Missing or invalid JWT token. |

## Data Formats
- **Content-Type**: `application/json`
- **Request/Response Objects**: Documented in `src/main/java/com/isteamx/university/dto/`.
