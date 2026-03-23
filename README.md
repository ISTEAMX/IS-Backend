# IS-Backend

This is the backend for the ISTEAMX project, built with Spring Boot.

## Running with Docker

The entire application stack can be run using Docker Compose from the `IS-DevOps` repository. See the `IS-DevOps` README for instructions. The backend will be available at [http://localhost:8080](http://localhost:8080).

## Running Locally

You can also run the backend directly on your local machine (e.g., from your IDE or using `mvn spring-boot:run`).

### Local Database Configuration

For local development, you need to provide database credentials.

1.  **Create a `.env` file:**
    In this directory (`IS-Backend`), you will find a file named `.env.example`. Make a copy of this file and name it `.env`.

    ```bash
    cp .env.example .env
    ```

2.  **Get the secret values:**
    Ask a team member for the correct values to put in your `.env` file.

The `application.properties` file is configured to get these credentials from environment variables. When running locally, most IDEs and tools can be configured to automatically load the `.env` file from the project root.

- **For IntelliJ IDEA:** The ".env files support" plugin will pick this up automatically.
- **For VS Code:** The "DotENV" extension will also work with a standard configuration.

If you do not create a `.env` file, the application will fall back to the default values specified in `application.properties` (`app_db`, `postgres`, `postgres`).
