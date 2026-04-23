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
1.  **Build Stage**: Uses a Maven image to resolve dependencies and build the JAR.
2.  **Runtime Stage**: Uses a lightweight JRE image (Java 21) to run the `app.jar`.

#### Running with Docker
To build and run the container locally:
```bash
docker build -t is-backend .
docker run -p 8080:8080 is-backend
```

## Configuration Management
The application identifies its runtime profile (e.g., `dev`, `prod`) and loads the appropriate `application.properties` or `application.yml`.

### Environment Variables
For production deployments, the following variables must be configured:
- `POSTGRES_DB`: Production database name (default: `app_db`).
- `POSTGRES_USER` / `POSTGRES_PASSWORD`: Production credentials (default: `postgres`).
- `JWT_SECRET_KEY`: A strong, randomly generated string for token signing.
- `JWT_EXPIRATION`: Token expiration time in milliseconds (default: `86400000` — 24 hours).
- `CLOUDWATCH_ENABLED`: Set to `true` to enable CloudWatch log shipping (default: `false`).
- `AWS_REGION`: AWS region for CloudWatch (default: `eu-central-1`).
- `CLOUDWATCH_LOG_GROUP`: CloudWatch Log Group name (default: `/isteamx/backend`).
- `CLOUDWATCH_LOG_STREAM`: CloudWatch Log Stream name (default: `app`).

## CI/CD Pipeline
(Currently, a GitHub Actions-based CI/CD pipeline is in development.)
The planned pipeline will:
1.  **Build & Test**: Run `./mvnw clean test`.
2.  **Push**: Upload the Docker image to a container registry.
3.  **Deploy**: Roll out the new version to an AWS EC2 instance or ECS cluster.
