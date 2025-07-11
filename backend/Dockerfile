# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy Gradle project files
COPY . .

# Build the app
RUN ./gradlew build

# Expose Spring Boot default port
EXPOSE 8080

# Run the app from the built JAR
CMD ["java", "-jar", "build/libs/bench-management-backend-0.0.1-SNAPSHOT.jar"]
