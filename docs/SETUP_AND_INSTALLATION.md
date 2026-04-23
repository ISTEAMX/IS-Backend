# Setup & Installation: IS-Backend

## Prerequisites
Before you begin, ensure you have the following installed on your local development machine:
- **Java 21 Development Kit (JDK)**: Recommended (e.g., OpenJDK or Eclipse Temurin).
- **Maven 3.9+**: For dependency management and build automation (or use the included Maven Wrapper `./mvnw`).
- **PostgreSQL 15+**: To host the database locally.
- **Git**: For version control.
- **A Code Editor**: [IntelliJ IDEA](https://www.jetbrains.com/idea/) is highly recommended for Spring Boot development.

## Step-by-Step Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd <repository-directory>/IS-Backend
```

### 2. Environment Variable Configuration
The application uses environment variables for database connections and security keys. You can set them in your shell, a `.env` file, or your IDE run configuration:

```env
POSTGRES_DB=app_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
JWT_SECRET_KEY=default-secret
JWT_EXPIRATION=86400000
```

- **POSTGRES_DB**: Database name (default: `app_db`).
- **POSTGRES_USER**: Database username (default: `postgres`).
- **POSTGRES_PASSWORD**: Database password (default: `postgres`).
- **JWT_SECRET_KEY**: Secret key for signing JWT tokens (default: `default-secret`).
- **JWT_EXPIRATION**: Token expiration time in milliseconds (default: `86400000` / 24 hours).

### 3. Build & Run locally

#### Using Maven Wrapper
Run the following command to build the project:
```bash
./mvnw clean install
```
Then, run the application:
```bash
./mvnw spring-boot:run
```
The backend will be available at [http://localhost:8080](http://localhost:8080).

## Database Migration
The application uses **Flyway** for database schema versioning. Migration scripts are located in `src/main/resources/db/migration/`. Flyway runs automatically on startup and applies any pending migrations. Hibernate's `ddl-auto` is set to `validate`, meaning it will verify the schema matches the JPA entities but will **not** make automatic schema changes — all schema modifications must go through Flyway migration scripts.

Ensure your PostgreSQL instance is running and the target database exists before starting the application.

## Swagger UI
Once the application is running, interactive API documentation is available at:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Other Common Commands
- **Run Unit Tests**: `./mvnw test`
- **Clean build**: `./mvnw clean`
- **Packaging (JAR file)**: `./mvnw package`
