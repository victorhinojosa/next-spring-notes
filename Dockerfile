# ===== STAGE 1: Build Frontend =====
FROM node:20.17.0-alpine AS frontend-builder
WORKDIR /app/frontend

# Copy dependency definitions first
COPY frontend/package*.json ./
RUN npm install --frozen-lockfile

# Copy the rest of the frontend source code
COPY frontend/ .

# Build Next.js static output
RUN npm run build


# ===== STAGE 2: Build Backend =====
FROM openjdk:21-jdk-slim AS backend-builder
WORKDIR /app

# Copy Gradle wrapper and build configuration
COPY gradlew .
COPY gradle ./gradle
COPY settings.gradle .
COPY build.gradle .
COPY backend/build.gradle ./backend/
COPY frontend/build.gradle ./frontend/

# Pre-download backend dependencies
RUN chmod +x ./gradlew
RUN ./gradlew :backend:dependencies --no-daemon || return 0

# Copy backend source code
COPY backend/src ./backend/src

# Run tests
RUN ./gradlew :backend:test --no-daemon

# Copy frontend build output into Spring Boot static resources
COPY --from=frontend-builder /app/frontend/out ./backend/src/main/resources/static/

# Build the Spring Boot fat JAR with Docker flag
RUN ./gradlew :backend:bootJar --no-daemon -Ddocker.build=true


# ===== STAGE 3: Runtime =====
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy only the built JAR from the builder stage
COPY --from=backend-builder /app/backend/build/libs/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]