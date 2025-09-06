# ===== STAGE 1: Build Frontend =====
FROM node:20.17.0-alpine AS frontend-builder

WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ .
RUN npm run build

# ===== STAGE 2: Build Backend =====
FROM openjdk:21-jdk-slim AS backend-builder

WORKDIR /app

# Copiar configuración Gradle
COPY gradlew .
COPY gradle ./gradle
COPY settings.gradle .
COPY build.gradle .
COPY backend/build.gradle ./backend/

# Copiar código backend
COPY backend/src ./backend/src

# Copiar archivos estáticos compilados del frontend
COPY --from=frontend-builder /app/frontend/out ./backend/src/main/resources/static/

RUN chmod +x ./gradlew
RUN ./gradlew :backend:bootJar -x test --no-daemon

# ===== STAGE 3: Runtime =====
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=backend-builder /app/backend/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]