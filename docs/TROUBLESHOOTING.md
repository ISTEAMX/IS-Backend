# Troubleshooting & Debugging: IS-Backend

## Common Issues & Solutions

### 1. Database Connection Failure
- **Error**: `Connection refused` when starting the Spring Boot application.
- **Solution**: Ensure your PostgreSQL service is running and accessible at the host and port specified in your `.env` or `application.properties`. Check the `POSTGRES_USER` and `POSTGRES_PASSWORD`.

### 2. JWT Authentication Issues
- **Error**: `403 Forbidden` for valid users or `401 Unauthorized`.
- **Solution**: Verify the `JWT_SECRET_KEY` matches between the authorization server and the resource server (if separated). Check the `JWT_EXPIRATION` setting to ensure tokens haven't expired.

### 3. Application Failures (Port Already in Use)
- **Error**: `Port 8080 is already in use`.
- **Solution**: Identify the process using the port and terminate it (e.g., `lsof -i :8080` followed by `kill -9 <PID>`) or change the `server.port` in your `application.properties`.

### 4. Hibernate Schema Update Failures
- **Error**: `Column 'X' already exists` or `Column mismatch`.
- **Solution**: If the schema update fails, you may need to manually drop the table and let Hibernate recreate it or use a migration tool like **Liquibase** or **Flyway** (not currently implemented).

## Debugging Techniques

### Spring Boot Logging
Use the application logs to trace issues:
- **Enable SQL logging**: Set `spring.jpa.show-sql=true` in `application.properties` to see the generated SQL.
- **Debug Security**: Set `logging.level.org.springframework.security=DEBUG` to trace the filter chain.

### JDB (Java Debugger) / IDE Debugging
Leverage your IDE's (IntelliJ IDEA, Eclipse) debugger to:
- **Set Breakpoints**: Inspect state and execution flow in specific controllers or services.
- **Inspect Variables**: View runtime values of DTOs and entities before they reach the data layer.

### API Testing Tools
Use **Postman** or **cURL** to:
- **Isolate Endpoints**: Test individual controllers without the full frontend.
- **Verify Headers**: Ensure the `Authorization` header is correctly formatted.
