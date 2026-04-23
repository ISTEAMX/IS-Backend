# IS-Backend 🚀

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/isteamx/is-backend)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://java.com/)

The core REST API for the **ISTEAMX** University Management System. Built with **Spring Boot** and **Java 21**, providing a secure and scalable foundation for academic resource scheduling.

---

## 🚀 Quick Start

### Prerequisites
- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Maven 3.9+](https://maven.apache.org/)
- [PostgreSQL 15+](https://www.postgresql.org/)

### Installation
```bash
# Clone the repository
git clone <repository-url>
cd IS-Backend

# Install dependencies and build
./mvnw clean install
```

### Running the App
```bash
# Start the Spring Boot application
./mvnw spring-boot:run
```
The API will be available at [http://localhost:8080](http://localhost:8080).

---

## ✨ Key Features
- **JWT Authentication**: Secure, stateless session management with role-based access.
- **Resource Management**: Comprehensive CRUD operations for Professors, Rooms, Student Groups, and Subjects.
- **Conflict-Aware Scheduling**: Intelligent university course scheduling that avoids overlaps.
- **Scalable Architecture**: Layered backend design optimized for high concurrency and performance.

---

## 🛠️ Technologies Used
- **Framework**: Spring Boot 4.0.4
- **Language**: Java 21 (Records, Virtual Threads)
- **Database**: PostgreSQL 15+
- **Migrations**: Flyway
- **Security**: Spring Security + JWT (jjwt 0.11.5)
- **Persistence**: Spring Data JPA (Hibernate)
- **API Docs**: springdoc-openapi (Swagger UI)
- **Monitoring**: Spring Boot Actuator + Micrometer CloudWatch
- **Error Tracking**: AWS CloudWatch Logs
- **Build Tool**: Maven
- **Utilities**: Lombok

---

## 📚 Project Documentation
For detailed information on the project's architecture, setup, and development guidelines, please refer to the files in the [docs/](docs/) folder:

- [**Project Overview**](docs/PROJECT_OVERVIEW.md): Comprehensive goals and audience description.
- [**Features**](docs/FEATURES.md): Functional walkthroughs of the backend capabilities.
- [**Architecture**](docs/ARCHITECTURE.md): Technical deep-dive into the Spring Boot layered structure.
- [**Setup & Installation**](docs/SETUP_AND_INSTALLATION.md): Detailed environment configuration and onboarding.
- [**Development Workflow**](docs/DEVELOPMENT_WORKFLOW.md): Coding standards, testing strategy, and branching.
- [**API Documentation**](docs/API_DOCUMENTATION.md): Detailed endpoint reference and security details.
- [**Database Design**](docs/DATABASE_DESIGN.md): Entity relationships, ERD, and table details.
- [**Build & Deployment**](docs/DEPLOYMENT.md): Docker multi-stage builds and production configuration.
- [**Troubleshooting**](docs/TROUBLESHOOTING.md): Common issues and debugging tips.

---

## 🤝 Contributing
We welcome contributions! Please refer to the [Development Workflow](docs/DEVELOPMENT_WORKFLOW.md) for guidelines on code style, testing, and pull request procedures.

---

## 📄 License
This project is licensed under the [MIT License](LICENSE) (or as per project policy).
