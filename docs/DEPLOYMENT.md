# Build & Deployment: IS-Backend

## Build Process
The project uses **Maven** for dependency management and lifecycle automation. To generate the executable JAR file, run:
```bash
./mvnw clean package
```
The output will be located in the `target/` directory.

## Deployment Strategy
We deploy the application as a **Docker** container, which allows for consistent environments and easy scalability.

### Dockerized Setup
The `Dockerfile` in the root directory uses a multi-stage approach:
1.  **Build Stage**: Uses a Maven 3.9 / Eclipse Temurin 21 image to resolve dependencies and build the JAR.
2.  **Runtime Stage**: Uses a lightweight `eclipse-temurin:21-jre-alpine` image to run the `app.jar`.

#### Running with Docker
To build and run the container locally:
```bash
docker build -t is-backend .
docker run -p 8080:8080 is-backend
```

## Database Migrations
The application uses **Flyway** for database schema versioning. Migration scripts are located in `src/main/resources/db/migration/`. Flyway runs automatically on application startup and applies any pending migrations. Hibernate's `ddl-auto` is set to `validate` to ensure the schema matches the entity definitions without making automatic changes.

## Configuration Management
The application loads configuration from `application.properties` and supports environment variable overrides for production deployments.

### Environment Variables
For production deployments, the following variables must be configured:
- `POSTGRES_DB`: Database name (default: `app_db`).
- `POSTGRES_USER`: Database username (default: `postgres`).
- `POSTGRES_PASSWORD`: Database password (default: `postgres`).
- `JWT_SECRET_KEY`: A strong, randomly generated string for token signing (default: `default-secret`).
- `JWT_EXPIRATION`: Token expiration time in milliseconds (default: `86400000` / 24 hours).

## CI/CD Pipeline
(Currently, a GitHub Actions-based CI/CD pipeline is in development.)
The planned pipeline will:
1.  **Build & Test**: Run `./mvnw clean test`.
2.  **Push**: Upload the Docker image to a container registry.
3.  **Deploy**: Roll out the new version to an AWS EC2 instance or ECS cluster.
