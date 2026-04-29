# Setup & Installation: IS-Backend

## Prerequisites
Before you begin, ensure you have the following installed on your local development machine:
- **Java 21 Development Kit (JDK)**: Recommended (e.g., OpenJDK or Temurin).
- **Maven 3.9+**: For dependency management and build automation.
- **PostgreSQL 15+**: To host the database locally (or use Docker — see below).
- **Docker & Docker Compose** *(recommended)*: For a consistent, cross-platform setup.
- **Git**: For version control.
- **A Code Editor**: [IntelliJ IDEA](https://www.jetbrains.com/idea/) is highly recommended for Spring Boot development.

## Step-by-Step Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd <repository-directory>/IS-Backend
```

### 2. Environment Variable Configuration
The application uses environment variables for database connections and security keys. Create a `.env` file in the `IS-Backend/` root (you can copy from the example):

```bash
cp .env.example .env
```

Default `.env` values:
```env
POSTGRES_DB=app_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
JWT_SECRET_KEY=ZzYxQzV2bU9WQkV3WjJrR2p1Zk1uQ0VYQm1aYlR5dHh4c1h1R0pWZz0=
JWT_EXPIRATION=86400000
DDL_AUTO=validate
FLYWAY_CLEAN_DISABLED=true
```

| Variable | Description |
|---|---|
| `POSTGRES_DB` | Database name |
| `POSTGRES_USER` | Database username |
| `POSTGRES_PASSWORD` | Database password |
| `JWT_SECRET_KEY` | Secret key for signing JWT tokens |
| `JWT_EXPIRATION` | Token expiration time in milliseconds (e.g., 86400000 = 24h) |
| `DDL_AUTO` | Hibernate DDL strategy — use `validate` (recommended) or `update` for dev |
| `FLYWAY_CLEAN_DISABLED` | Protects against accidental DB wipe — keep `true` unless troubleshooting |

### 3. Start the Database

#### Option A: Using Docker Compose (Recommended — works on every machine)
From the **project root** (the `ISTEAMX/` directory):
```bash
# Start only the database container
docker compose -f IS-DevOps/docker-compose/docker-compose.yml up -d database

# Verify it's healthy
docker compose -f IS-DevOps/docker-compose/docker-compose.yml ps
```
The PostgreSQL database will be available at `localhost:5432`.

#### Option B: Using a local PostgreSQL installation
Ensure your PostgreSQL service is running, then create the database:
```bash
psql -U postgres -c "CREATE DATABASE app_db;"
```

### 4. Build & Run Locally

#### Using Maven Wrapper
```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```
The backend will be available at [http://localhost:8080](http://localhost:8080).

#### Using Docker Compose (full stack)
```bash
# From the project root (ISTEAMX/ directory)
docker compose -f IS-DevOps/docker-compose/docker-compose.yml up -d
```
This starts the backend, frontend, and database together.

## Database Migration (Flyway)

The application uses **Flyway** for database schema management. Key configuration:

| Property | Value | Description |
|---|---|---|
| `spring.flyway.enabled` | `true` | Flyway runs automatically on startup |
| `spring.flyway.baseline-on-migrate` | `true` | Creates baseline for existing databases |
| `spring.flyway.baseline-version` | `0` | Baseline version (all V1+ migrations will run) |
| `spring.flyway.out-of-order` | `true` | Allows migrations added out of order (useful for team dev) |
| `spring.flyway.encoding` | `UTF-8` | Ensures Romanian characters in SQL files work cross-platform |
| `spring.flyway.clean-disabled` | `true` | Prevents accidental database wipe |

Migration files are located in `src/main/resources/db/migration/` and follow the naming convention `V<number>__<Description>.sql`.

Hibernate's `ddl-auto` is set to `validate`, meaning it will verify the schema matches the entity mappings but will **not** modify the database — all schema changes **must** be done via new Flyway migration files.

### Creating a New Migration
```bash
# Example: adding a new column
touch src/main/resources/db/migration/V7__Add_new_column.sql
```
Then write your SQL in the file. **Never modify an existing migration file** that has already been applied.

### Troubleshooting Flyway Errors
If you encounter Flyway migration errors (checksum mismatch, failed migration, etc.), see the detailed guide in [TROUBLESHOOTING.md](TROUBLESHOOTING.md#5-flyway-migration-errors).

**Quick fix — fresh database reset:**
```bash
# Docker users: drop the volume and restart
docker compose -f IS-DevOps/docker-compose/docker-compose.yml down -v
docker compose -f IS-DevOps/docker-compose/docker-compose.yml up -d

# Local PostgreSQL users:
psql -U postgres -c "DROP DATABASE IF EXISTS app_db;"
psql -U postgres -c "CREATE DATABASE app_db;"
# Then restart the application
```

## Other Common Commands
- **Run Unit Tests**: `./mvnw test`
- **Clean build**: `./mvnw clean`
- **Packaging (JAR file)**: `./mvnw package`
