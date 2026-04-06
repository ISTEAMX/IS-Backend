# API Documentation: IS-Backend

## Overview
The `IS-Backend` provides a RESTful API designed for managing university resources. All endpoints are prefixed with `/api`.

## Authentication (`/user`)
| Method | Path | Description | Request Body | Response |
|--|--|--|--|--|
| POST | `/register` | Register a new user | `UserDTO` | `{"message": "User registered"}` |
| POST | `/login` | Authenticate and get JWT | `LoginDTO` | `ResponseLoginDTO` |

### Security Mechanism
1.  **Request Login**: User sends credentials via POST `/api/user/login`.
2.  **Generate Token**: Server validates and returns a signed JWT.
3.  **Authorized Requests**: Include the JWT in the `Authorization: Bearer <token>` header.
4.  **Filter**: `JwtAuthenticationFilter` intercepts requests to verify the token.

## Resource Management

### Room Management (`/room`)
| Method | Path | Description | Roles Required |
|--|--|--|--|
| GET | `/user/{id}` | Get room details by ID | Public/User |
| GET | `/user/rooms` | List all available rooms | Public/User |
| POST | `/create` | Create a new room | Admin |
| PUT | `/update` | Update existing room | Admin |
| DELETE | `/delete/{id}` | Remove a room | Admin |

### Schedule Management (`/schedule`)
| Method | Path | Description | Roles Required |
|--|--|--|--|
| GET | `/user/{id}` | Get specific schedule entry | Public/User |
| GET | `/user` | List all schedules | Public/User |
| POST | `/add` | Add new schedule entry | Admin |
| PUT | `/update` | Modify schedule entry | Admin |
| DELETE | `/delete/{id}` | Remove schedule entry | Admin |
| GET | `/user/filter` | Filter by Room/Group/Prof | Public/User |

## Error Responses
The system uses a centralized `GlobalExceptionHandler` to ensure consistent error responses.

| Exception | HTTP Status | Meaning |
|-----------|-------------|---------|
| `ResourceNotFound` | 404 Not Found | Entity does not exist. |
| `AlreadyExists` | 409 Conflict | Violation of unique constraints (e.g., email). |
| `AccessDenied` | 403 Forbidden | User lacks necessary permissions. |
| `Unauthorized` | 401 Unauthorized | Missing or invalid JWT token. |

## Data Formats
- **Content-Type**: `application/json`
- **Request/Response Objects**: Documented in `src/main/java/com/isteamx/university/dto/`.
