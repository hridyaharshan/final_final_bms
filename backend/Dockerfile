FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . .

# 🔥 Make gradlew executable
RUN chmod +x ./gradlew

# 🔨 Build the Spring Boot app
RUN ./gradlew build

# 🌐 Expose port
EXPOSE 8080

# 🚀 Start the app
CMD ["java", "-jar", "build/libs/bench-management-backend-0.0.1-SNAPSHOT.jar"]
