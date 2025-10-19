
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

### Open the interactive documentation

1. Start the application as described in [Getting Started](#getting-started).
2. Navigate to the Swagger UI at <http://localhost:8080/swagger-ui/index.html>.

### Create a client (here Person example)

- **Endpoint**: `POST /api/clients/persons`
- **Body (Person example)**:
  ```json
  {
    "name": "Alice",
    "email": "alice@example.com",
    "phone": "+41790000000",
    "birthDate": "1990-05-01"
  }
  ```
- Click **Execute** → the API returns the identifier (UUID) of the created client.

### Read a client

- **Endpoint**: `GET /api/clients/{id}`
- Paste the UUID obtained previously and execute → all client information is returned.

### Update a client

- **Endpoint**: `PUT /api/clients/{id}`
- **Body**: provide `name`, `email`, and `phone` to verify that the fields are updated correctly.

### Create a contract for a client

- **Endpoint**: `POST /api/clients/{id}/contracts`
- **Body**:
  ```json
  {
    "startDate": "2023-10-19",
    "endDate": "2026-10-19",
    "costAmount": 123456
  }
  ```
- `startDate` is added automatically and `updatedAt` is managed internally.

### List active contracts

- **Endpoint**: `GET /api/clients/{id}/contracts`
- The created contract appears in the response. Test the `updatedAfter=YYYY-MM-DD` filter by adding the query parameter.

### Update the cost of a contract

- **Endpoint**: `PATCH /api/clients/{id}/contracts/{contractId}/cost`
- **Body**:
  ```json
  { "newCostAmount": 2500 }
  ```
- Verify that `costAmount` changes and the contract remains active.

### Calculate the sum of active contracts

- **Endpoint**: `GET /api/clients/{id}/contracts/total-cost`
- The returned total matches the sum of all active contracts for the client.

### Delete a client

- **Endpoint**: `DELETE /api/clients/{id}`
- All of the client's contracts have their `endDate` updated to today.

Dates must follow the ISO 8601 format (`YYYY-MM-DD`). Phone numbers and email addresses are validated by the API.

### How to test

1. Start the database and application as described in [Getting Started](#getting-started).

2. Exercise the REST endpoints manually via `curl` (see above) or through the Swagger UI:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```
3. Inspect the PostgreSQL database to confirm that clients and contracts are persisted.
