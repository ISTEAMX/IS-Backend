# Build & Deployment: IS-Backend

## Build Process
The project uses **Maven** for dependency management and lifecycle automation. To generate the executable JAR file, run:
```bash
./mvnw clean package
```
The output will be located in the `target/` directory.

## Deployment Strategy
We deploy the application as a **Docker** container on an **AWS EC2** instance, with images stored in **AWS ECR** (Elastic Container Registry).

### Dockerized Setup
The `Dockerfile` in the root directory uses a multi-stage approach:
1.  **Build Stage**: Uses a Maven image to resolve dependencies offline and build the JAR.
2.  **Runtime Stage**: Uses a lightweight JRE Alpine image (Java 21) to run the `app.jar`.

#### Running with Docker (Local)
To build and run the container locally:
```bash
docker build -t is-backend .
docker run -p 8080:8080 is-backend
```

## Configuration Management
The application reads configuration from `application.properties` with environment variable overrides for production.

### Environment Variables
For production deployments, the following variables are configured via **AWS Secrets Manager** (`isteamx/backend`):

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | Full JDBC URL (overrides the properties file) | — |
| `POSTGRES_DB` | Database name | `app_db` |
| `POSTGRES_USER` | Database username | `postgres` |
| `POSTGRES_PASSWORD` | Database password | `postgres` |
| `JWT_SECRET_KEY` | JWT signing secret (Base64-encoded) | **Required — no default** |
| `JWT_EXPIRATION` | Token expiration in milliseconds | `86400000` (24h) |
| `CLOUDWATCH_ENABLED` | Enable CloudWatch log shipping | `false` |
| `AWS_REGION` | AWS region for CloudWatch | `eu-central-1` |

> **Note:** `JWT_SECRET_KEY` has no default value — the application will fail to start without it. This is intentional to prevent insecure defaults in production.

## CI/CD Pipeline (GitHub Actions)

The backend uses a fully automated CI/CD pipeline via reusable workflows in `IS-DevOps`:

### Build Pipeline (on push/PR to `main`)
**Workflow**: `build.yml` → calls `IS-DevOps/continuous-build-backend.yml`
1. **Test**: `mvn -B test` with JDK 21 and Maven caching
2. **Package**: `mvn -B package -DskipTests`
3. **Docker Build**: Validates the Dockerfile builds successfully

### Release Pipeline (on push to `main`)
**Workflow**: `release.yml` → calls `IS-DevOps/continuous-release-backend.yml`
1. Reads current version from `pom.xml`
2. Calculates next semantic version (patch by default)
3. Creates a git tag (e.g., `v1.0.11`)
4. Bumps `pom.xml` to next SNAPSHOT version
5. Creates a GitHub Release

### Deploy Pipeline (manual trigger)
**Workflow**: `deploy.yml` → calls `IS-DevOps/continuous-deployment-backend.yml`
1. **Security**: Opens SSH port temporarily for the GitHub runner's IP only
2. **Build & Push**: Builds Docker image, tags with commit SHA, pushes to ECR
3. **Deploy to EC2** (via SSH):
   - Logs into ECR on the EC2 instance
   - Pulls the latest image
   - Fetches secrets from AWS Secrets Manager → writes to `.env` (permissions `600`)
   - Stops and removes the old container
   - Starts new container with `--restart unless-stopped` (survives EC2 reboots)
4. **Cleanup**: Removes the temporary SSH rule

### Database Migrations
- **Flyway** runs automatically on application startup
- Migrations are in `src/main/resources/db/migration/` (V1 through V6)
- `spring.jpa.hibernate.ddl-auto=validate` — Hibernate validates but never modifies the schema
- **Never edit** an already-applied migration file — always create a new `V7__Description.sql`
