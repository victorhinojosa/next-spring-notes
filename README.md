# Next Spring Notes

A modern note-taking application built with Next.js and Spring Boot, following hexagonal architecture principles.

![Demo](demo.gif)

## Features
- Create and view notes
- Edit notes
- 🎯 Coming soon:
  - Delete notes

## Tech Stack
### Frontend
- Next.js 
- TypeScript
- Material UI
- React Hooks
- Custom components

### Backend
- Spring Boot 
- Java
- JUnit for testing
- MySQL database
- RESTful APIs

## Architecture
- Hexagonal Architecture
- Clean code principles
- SOLID principles
- Domain-Driven Design concepts

## Project Structure

This project uses a **hybrid development approach** optimized for both local development and production deployment:

### Development Workflow

**Local Development (Recommended):**
```bash

# Terminal 1: Start backend API server
# Using IntelliJ: Run Application.main() directly (fastest)
# Backend runs on http://localhost:8080

# Terminal 2: Start frontend development server  
cd frontend
npm run dev
# Frontend runs on http://localhost:3000 with hot reload
```

### Docker Deployment

The application uses a **multi-stage Docker build** for production:

1. **Stage 1**: Build Next.js static assets
2. **Stage 2**: Build Spring Boot application and integrate frontend assets
3. **Stage 3**: Create minimal runtime image

```bash

# Using Docker Compose (recommended)
docker-compose up --build

# Using Docker directly
docker build -t mindkeep-app .
docker run -p 8080:8080 --env-file .env.docker mindkeep-app
```

### Build Configuration

- **Root**: Multi-module Gradle project configuration
- **Frontend**: Node.js/npm build with Next.js static export
- **Backend**: Spring Boot with conditional frontend integration
- **Docker**: Optimized multi-stage build with environment variable support

The build system intelligently handles different scenarios:
- **Local development**: Frontend and backend run separately for fast iteration
- **Production builds**: Frontend assets are compiled and integrated into the Spring Boot JAR automatically via Docker

### Environment Configuration

- **Local**: Uses application.yml profiles
- **Docker**: Uses .env.docker file with docker-compose
- **CORS**: Configured for cross-origin requests during development

## API Endpoints
- `GET /api/notes`: Retrieve all notes
- `PUT /api/notes`: Create a new note
- `PATCH /api/notes/{id}`: Update a note
- [WIP] `DELETE /api/notes/{id}`: Delete a note