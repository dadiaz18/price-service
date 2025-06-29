## 📦 Price Service - Java Hexagonal Architecture

This is a Spring Boot application built to provide pricing information for a given product, brand, and date. It follows the **Hexagonal Architecture** pattern (also known as Ports and Adapters) and uses an in-memory H2 database initialized with sample pricing data.

---

### ✅ Features

* Exposes a REST endpoint to retrieve applicable product prices.
* Applies the correct price based on date range and highest priority.
* Implements **Hexagonal Architecture**:

    * Domain-driven design.
    * Clean separation between use cases and infrastructure.
* Includes full unit and integration test coverage.

---

### 📂 Project Structure

```
src/
├── main/
│   ├── java/com.diego.priceservice/
│   │   ├── application/
│   │   │   └── usecase/                  # Use case implementation
│   │   ├── domain/
│   │   │   ├── model/                    # Business entities
│   │   │   └── ports/
│   │   │       ├── in/                   # Input ports (use case interfaces)
│   │   │       └── out/                  # Output ports (repository interfaces)
│   │   ├── infrastructure/
│   │   │   ├── controller/               # REST controllers
│   │   │   ├── repository/               # JPA adapters
│   │   │   └── config/                   # Spring configuration
│   │   └── PriceServiceApplication.java # Main entry point
│   └── resources/
│       ├── application.properties
│       ├── data.sql                      # Initial dataset
│       └── schema.sql                    # Database schema
├── test/
│   ├── application.usecase/              # Unit tests for use cases
│   └── infrastructure.controller/        # Integration tests for REST endpoints
```

---

### 📡 REST API

#### Endpoint

```http
GET /prices
```

#### Query Parameters

* `applicationDate` (ISO 8601 format) – Date and time of the query
* `productId` – ID of the product
* `brandId` – ID of the brand

#### Example

```
GET /prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1
```

#### Response (if applicable price found)

```json
{
  "brandId": 1,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "priceList": 2,
  "productId": 35455,
  "priority": 1,
  "price": 25.45,
  "currency": "EUR"
}
```

---

### 🧪 Tests

Includes unit tests for the main use case and integration tests for the REST controller.

Test cases implemented:

1. 2020-06-14 10:00 → product `35455`, brand `1`
2. 2020-06-14 16:00 → product `35455`, brand `1`
3. 2020-06-14 21:00 → product `35455`, brand `1`
4. 2020-06-15 10:00 → product `35455`, brand `1`
5. 2020-06-16 21:00 → product `35455`, brand `1`

---

### 🚀 Run the project

```bash
./mvnw spring-boot:run
```

Then open:
`http://localhost:8080/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1`

To access H2 console:
`http://localhost:8080/h2-console`
JDBC URL: `jdbc:h2:mem:pricesdb`

---

### 📋 Tech Stack

* Java 17
* Spring Boot 3+
* H2 In-Memory Database
* JPA / Hibernate
* JUnit & Spring Boot Test
* Hexagonal Architecture (DDD, ports & adapters)

---

### 🛠️ How to Run

Make sure you have **Java 17+** installed. You don't need to install Maven because this project uses the Maven Wrapper.

To run the project locally:

```bash
./mvnw spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

Once running, open in your browser:

```
http://localhost:8080/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1
```

To access the H2 database console:

```
http://localhost:8080/h2-console
```

Use this JDBC URL:

```
jdbc:h2:mem:pricesdb
```

---

### 📥 How to Clone

```bash
git clone https://github.com/dadiaz18/price-service.git
cd price-service
./mvnw clean install
```
