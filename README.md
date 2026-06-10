# Mission Control Dashboard

Full-stack satellite telemetry monitoring system built with Java Spring Boot and React. Simulates real-time data for three orbital assets: ISS, Sentinel-1A and Galileo-FOC1.

**Live demo:** https://mission-control-frontend-0dx7.onrender.com  
**Backend API:** https://mission-control-dashboard-ju8n.onrender.com/api/satellites

---

## What it does

The backend generates and updates telemetry data every 5 seconds using a scheduled task. Each satellite has altitude, velocity, battery and temperature readings that fluctuate realistically. When battery drops below 20% or temperature goes out of range, the system generates a HIGH severity alert and changes the satellite status to WARNING.

The frontend polls the API every 3 seconds, displaying live satellite cards and a real-time telemetry chart for the selected satellite.

---

## Architecture

```
React Frontend (Render Static Site)
        |
        | HTTP polling every 3s
        v
Spring Boot REST API (Render Web Service)
        |
        | JPA / Hibernate
        v
PostgreSQL (Render Managed Database)
```

---

## Backend

**Stack:** Java 21, Spring Boot 3.2, Spring Data JPA, Hibernate, PostgreSQL, Maven, Docker

**Key components:**

- `MissionService` — core business logic. Initializes three satellites on first run and runs a `@Scheduled` task every 5 seconds to update telemetry values with realistic variation. Generates alerts when critical thresholds are exceeded.
- `MissionController` — REST API with three endpoints, CORS configured for cross-origin access.
- `Satellite` / `Alert` — JPA entities persisted to PostgreSQL.

**API endpoints:**

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/satellites` | All satellites with current telemetry |
| GET | `/api/alerts` | All alerts |
| GET | `/api/alerts/{name}` | Alerts filtered by satellite name |

**Alert thresholds:**

| Parameter | Threshold | Severity |
|-----------|-----------|----------|
| Battery | < 20% | HIGH |
| Temperature | > 50°C or < -60°C | HIGH |

---

## Frontend

**Stack:** React, Recharts, Axios

- Satellite list with live status badges (NOMINAL / WARNING)
- Real-time telemetry chart showing velocity and battery history for the selected satellite
- Alerts log with severity indicators
- Auto-refresh every 3 seconds

---

## Deployment

The system is fully deployed on Render:

- **Frontend** — Static Site, built with `npm run build`
- **Backend** — Docker container, multi-stage build with Maven + Eclipse Temurin 21 JRE
- **Database** — Render Managed PostgreSQL, connected via `DATABASE_URL` environment variable

Backend Dockerfile uses a multi-stage build to keep the final image lean:

```dockerfile
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/mission-control-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## Local setup

**Prerequisites:** Java 21, Maven, PostgreSQL

```bash
# Clone the repository
git clone https://github.com/Argenika/mission-control-dashboard
cd mission-control-dashboard

# Create local database
createdb missiondb

# Configure credentials in src/main/resources/application.properties
spring.datasource.username=your_postgres_user
spring.datasource.password=your_postgres_password

# Run the backend
mvn spring-boot:run
```

Backend runs on `http://localhost:8080`.

For the frontend:

```bash
cd mission-control-frontend
npm install
npm start
```

Frontend runs on `http://localhost:3000`.

---

## Developed by

**Angelina Lepeshko** — Full-Stack Developer, Valencia  
[github.com/Argenika](https://github.com/Argenika) · [LinkedIn](https://linkedin.com/in/angelina-lepeshko-b9a410329/) · [Portfolio](https://angelinalepeshko.netlify.app)
