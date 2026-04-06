# Development Workflow: IS-Backend

## Code Structure
We follow a layered architecture implemented in **Spring Boot**. The core directories are:
```text
src/main/java/com/isteamx/university/
├── controller/     # Handle incoming requests and responses
├── service/        # Business logic and coordination
├── repository/     # Data access and ORM mapping
├── model/          # Entities and internal data structures
├── dto/            # Data Transfer Objects for API
├── mapper/         # Converters between Entities and DTOs
├── security/       # JWT configuration and security filters
└── exception/      # Global exception handling logic
```

## Branching Strategy
We recommend using a feature-based branching strategy:
- `main`: Production-ready code.
- `develop`: Ongoing development.
- `feature/*`: New functionalities.
- `bugfix/*`: Bug fixes.

## Coding Standards & Best Practices
- **Java 21 Syntax**: Use records, switch expressions, and virtual threads where appropriate.
- **Lombok Usage**: Use `@Getter`, `@Setter`, and `@RequiredArgsConstructor` to minimize boilerplate.
- **Separation of Concerns**: Keep Controllers light and move business logic into Service classes.
- **DTO Mapping**: Never expose JPA entities directly via APIs; always use DTOs.
- **Global Error Handling**: Standardize error messages through the `GlobalExceptionHandler`.

## Testing Strategy
We aim for high test coverage across critical business logic.
- **Unit Testing**: [JUnit 5](https://junit.org/junit5/) and [Mockito](https://site.mockito.org/) for service-level testing.
- **Integration Testing**: Test real database interactions with [H2](https://www.h2database.com/html/main.html) or [Testcontainers](https://testcontainers.com/).

### Running Tests
```bash
./mvnw test
```
Tests are located in `src/test/java/com/isteamx/university/`.

## API Documentation First
When adding new endpoints:
1.  **Define DTOs**: Create the necessary request/response objects.
2.  **Document API**: Update `API_DOCUMENTATION.md` with new methods and paths.
3.  **Implement Logic**: Start with the repository/service layer before creating the controller.
