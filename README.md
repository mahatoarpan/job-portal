# Job Portal Microservices

A Spring Boot microservices-based application consisting of:
- **companyms** – Manages companies
- **jobs** – Manages job postings
- **reviews** – Manages company reviews
- **service-reg** – Eureka service registry
- **gateway** – API Gateway for routing requests

---

## 🚀 Prerequisites
Make sure you have installed:
- **Java 21**
- **Maven 3.9+**
- **Docker**

---

## Step 1:  Build the Project
This will generate JAR files for each microservice inside their `target/` directories.
```bash
mvn clean package -DskipTests
```


## Step 2: Build Docker Images
Run the following from the root of the project:

```bash
docker build -t company-service ./companyms
docker build -t job-service ./jobs
docker build -t review-service ./reviews
docker build -t service-reg ./service-reg
docker build -t gateway-service ./gateway
```

## Step 3: Run All Services with Docker Compose

We use docker-compose.yml to start everything together.
```bash
docker compose up -d
```

This will start:
* Eureka Server at http://localhost:8761
* API Gateway at http://localhost:8084
* Other microservices registered with Eureka

