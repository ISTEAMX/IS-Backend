# IS-Backend

The backend for the ISTEAMX project, built with **Spring Boot 4** and **Java 21**.

## Tech Stack

- **Framework:** Spring Boot 4.0
- **Language:** Java 21
- **Database:** PostgreSQL
- **Auth:** JWT (JSON Web Tokens)
- **Build Tool:** Maven

---

## Running with Docker (Local Development)

The entire application stack (backend, frontend, database) can be run using Docker Compose from the `IS-DevOps` repository. See the [IS-DevOps README](../IS-DevOps/README.md) for instructions.

```bash
# From the IS-DevOps/ directory
docker-compose -f docker-compose/docker-compose.yml up --build -d
```

The backend will be available at [http://localhost:8080](http://localhost:8080).

---

## Running Locally (IDE / Maven)

You can also run the backend directly on your local machine (e.g., from your IDE or using `mvn spring-boot:run`).

### Prerequisites

- Java 21
- Maven 3.9+
- A running PostgreSQL instance (or use `docker run -d -p 5432:5432 -e POSTGRES_DB=app_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres:15`)

### Environment Configuration

1. **Create a `.env` file:**
   Copy the provided example and fill in any secret values.

   ```bash
   cp .env.example .env
   ```

2. **Get the secret values:** Ask a team member for the correct production values, or use the defaults for local development.

### Environment Variables

| Variable            | Description                        | Default        |
|---------------------|------------------------------------|----------------|
| `POSTGRES_DB`       | PostgreSQL database name           | `app_db`       |
| `POSTGRES_USER`     | PostgreSQL username                | `postgres`     |
| `POSTGRES_PASSWORD` | PostgreSQL password                | `postgres`     |
| `JWT_SECRET_KEY`    | Secret key for signing JWT tokens  | `default-secret` |
| `JWT_EXPIRATION`    | JWT token expiration time (ms)     | `86400000`     |

The `application.properties` file reads these from environment variables with the defaults shown above. Most IDEs can load the `.env` file automatically:

- **IntelliJ IDEA:** The "EnvFile" or ".env files support" plugin will pick this up.
- **VS Code:** The "DotENV" extension works with a standard configuration.

### Start the Application

```bash
./mvnw spring-boot:run
```

---

## AWS Deployment

In production, the backend is deployed as a Docker container on an **EC2 instance** (`t3.micro`, `eu-central-1`). The Docker image is stored in **AWS ECR**.

The EC2 instance is provisioned via Terraform with a user-data script that automatically installs Docker, AWS CLI, and starts a PostgreSQL container. See the [IS-DevOps README](../IS-DevOps/README.md) for full deployment instructions.

| Resource    | Details                         |
|-------------|---------------------------------|
| EC2         | `t3.micro`, Ubuntu 22.04       |
| Elastic IP  | `35.158.14.254`                 |
| ECR         | `isteamx-backend`              |
| Region      | `eu-central-1`                  |
