# Stage 1: Build the application
FROM maven:3.9.12-amazoncorretto-25 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package 

# Stage 2: Create the final runtime image
#FROM amazoncorretto:25-jdk
FROM maven:3.9.12-amazoncorretto-25
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

