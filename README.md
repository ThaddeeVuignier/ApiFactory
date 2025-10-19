
## About The Project

ApiFactory is a REST API backend for managing insurance clients and their contracts. It supports two client types (Person and Company) with full CRUD operations, contract lifecycle management, and optimized queries for active contract aggregation.

**Key Features:**
- Client management with immutable identifiers (birthdate/company ID)
- Contract CRUD with automatic date tracking and soft-delete on client removal
- Active contract filtering and DB-side cost aggregation
- Full Bean Validation (email, phone, ISO 8601 dates)
- Hexagonal architecture for clean separation of concerns

**Architecture:** Multi-module Maven project (`domain` → `application` → `infrastructure`) following hexagonal/ports-adapters pattern for maintainability and testability.


### Built With

- ![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.6-green?logo=springboot)
- ![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker)


## Getting Started

### Prerequisites

- **JDK 21** - [Download OpenJDK](https://adoptium.net/)
- **Docker & Docker Compose** - [Get Docker](https://www.docker.com/get-started)
- **Maven 3.9+** (or use included wrapper `./mvnw`)


### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/ThaddeeVuignier/ApiFactory.git
   cd ApiFactory
   ```

2. Start PostgreSQL
   ```bash
   docker compose up -d
   ```

3. Build all modules
   ```bash
   ./mvnw clean install
   ```

4. Run the application
   ```bash
   ./mvnw -pl infrastructure spring-boot:run
   ```

API available at: `http://localhost:8080/swagger-ui/index.html`

## Usage
