# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy the pom.xml first (for dependency caching)
COPY pom.xml .

# Download dependencies (this layer will be cached)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17.0.1-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/careersite-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]