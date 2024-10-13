# Use a base image with JDK for building
FROM maven:3.8.5-openjdk-17-slim as builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

# Use the same base image for the runtime
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/home-service-provider-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
