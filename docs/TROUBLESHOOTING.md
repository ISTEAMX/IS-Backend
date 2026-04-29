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

### 4. Hibernate Schema Validation Failures
- **Error**: `Schema-validation: missing table` or `Schema-validation: missing column`.
- **Solution**: The application uses `spring.jpa.hibernate.ddl-auto=validate`, so Hibernate will **not** create or modify tables. Ensure all required Flyway migrations in `src/main/resources/db/migration/` have been applied. If the schema is out of sync, create a new Flyway migration file (e.g., `V7__Add_column.sql`) to update the schema.

### 5. Flyway Migration Errors

Flyway migration errors typically happen when:
- A migration file was modified after it was already applied (checksum mismatch).
- The database was manually altered outside of Flyway migrations.
- A migration partially failed, leaving the schema in an inconsistent state.

#### Error: `Migration checksum mismatch`
```
Flyway: Validate failed: Migrations have failed validation
Migration checksum mismatch for migration version X
```
**Solution**: This means a `.sql` file was edited after it was already applied. **Never edit an already-applied migration.** Instead, create a new migration file with the fix. If you are in a **local development** environment and want to start fresh, see [Nuclear Option: Full Database Clean & Rebuild](#nuclear-option-full-database-clean--rebuild) below.

#### Error: `Migration failed` (partial execution)
```
Flyway: Migration of schema "public" to version X failed!
```
**Solution**: The migration SQL has an error. Fix the SQL file, then repair Flyway's history table:

**Option A — Via psql (connect to the database):**
```bash
# Connect to the database
psql -h localhost -U postgres -d app_db

# Check which migrations have been applied
SELECT * FROM flyway_schema_history ORDER BY installed_rank;

# Remove the failed migration entry so Flyway retries it
DELETE FROM flyway_schema_history WHERE success = false;

# Exit psql
\q
```
Then fix the SQL file and restart the application.

**Option B — Via Docker (if using docker-compose):**
```bash
# Connect to the PostgreSQL container
docker exec -it <container-name-or-id> psql -U postgres -d app_db

# Remove failed migration entries
DELETE FROM flyway_schema_history WHERE success = false;
\q
```

#### Nuclear Option: Full Database Clean & Rebuild

> ⚠️ **WARNING**: This will **delete all data** in your database. Only use in local development!

**Method 1 — Drop and recreate the database:**
```bash
# If running PostgreSQL locally
psql -h localhost -U postgres -c "DROP DATABASE IF EXISTS app_db;"
psql -h localhost -U postgres -c "CREATE DATABASE app_db;"

# If using Docker Compose
docker compose -f IS-DevOps/docker-compose/docker-compose.yml down -v
docker compose -f IS-DevOps/docker-compose/docker-compose.yml up -d database
```
Then restart the application — Flyway will re-run all migrations from scratch.

**Method 2 — Using Flyway Clean (requires config change):**

1. Temporarily set in your `.env`:
   ```
   FLYWAY_CLEAN_DISABLED=false
   ```
2. Connect to the database and run:
   ```sql
   -- This will drop ALL objects in the schema
   -- Only works if spring.flyway.clean-disabled=false
   ```
   Or add a temporary `@Bean` in your Spring Boot app:
   ```java
   @Bean
   CommandLineRunner flywayClean(Flyway flyway) {
       return args -> {
           flyway.clean();
           flyway.migrate();
       };
   }
   ```
3. **Remember to set `FLYWAY_CLEAN_DISABLED=true` back** after cleaning!

**Method 3 — Docker volume reset (recommended for Docker users):**
```bash
# Stop everything and remove the database volume
docker compose -f IS-DevOps/docker-compose/docker-compose.yml down -v

# Start fresh — Flyway will run all migrations on a clean database
docker compose -f IS-DevOps/docker-compose/docker-compose.yml up -d
```

#### Tips to Avoid Flyway Errors
- **Never modify** a migration file after it has been applied to any database.
- **Always create a new versioned file** (e.g., `V7__Fix_something.sql`) for schema changes.
- **Use `INSERT ... ON CONFLICT DO NOTHING`** in seed migrations to make them re-runnable.
- **Test migrations locally** before pushing: drop your local DB and re-run from scratch.

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
