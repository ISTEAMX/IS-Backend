# Development Workflow: IS-Backend

## Code Structure
We follow a layered architecture implemented in **Spring Boot**. The core directories are:
```text
src/main/java/com/isteamx/university/
├── controller/     # Handle incoming requests and responses
├── service/        # Business logic interfaces
│   └── impl/       # Business logic implementations
├── repository/     # Data access and ORM mapping
├── entity/         # JPA entities and internal data structures
├── dto/            # Data Transfer Objects for API
├── dtoMapper/      # Converters between Entities and DTOs
├── configuration/  # Security config, JWT filter, application config
├── exception/      # Global exception handling logic
├── enums/          # Enumerations (e.g., Frequency)
├── monitoring/     # CloudWatch error reporter and error report controller
├── config/         # OpenAPI/Swagger configuration
└── util/           # Utility classes (e.g., JwtUtil)
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
- **Unit Testing**: [JUnit 5](https://junit.org/junit5/) via `spring-boot-starter-data-jpa-test` and `spring-boot-starter-webmvc-test`.
- **Integration Testing**: [H2 in-memory database](https://www.h2database.com/html/main.html) for test-scoped database interactions.

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
