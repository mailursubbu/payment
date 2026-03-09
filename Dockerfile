# Stage 1: Build the application
FROM maven:3.9.12-amazoncorretto-25 AS builder
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/*.jar app.jar

# Activate Spring profile "db" (e.g. for DB-backed repos)
ENV SPRING_PROFILES_ACTIVE=db

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
