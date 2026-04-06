# Setup & Installation: IS-Backend

## Prerequisites
Before you begin, ensure you have the following installed on your local development machine:
- **Java 21 Development Kit (JDK)**: Recommended (e.g., OpenJDK or Temurin).
- **Maven 3.9+**: For dependency management and build automation.
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
The application uses environment variables for database connections and security keys. Create a `.env` file in the root directory and add the following:

```env
POSTGRES_DB=app_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
JWT_SECRET_KEY=default-secret
JWT_EXPIRATION=86400000
```

- **POSTGRES_DB**: Database name.
- **POSTGRES_USER**: Database username.
- **POSTGRES_PASSWORD**: Database password.
- **JWT_SECRET_KEY**: Secret key for signing JWT tokens.
- **JWT_EXPIRATION**: Token expiration time in milliseconds (e.g., 24 hours).

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
The application uses Hibernate's `ddl-auto: update` (default in development) or `create-drop` to manage the schema. Ensure your PostgreSQL instance is running before starting the application.

## Other Common Commands
- **Run Unit Tests**: `./mvnw test`
- **Clean build**: `./mvnw clean`
- **Packaging (JAR file)**: `./mvnw package`
