# Use a multi-stage build to keep the final image small
# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build the application and create the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre-noble

# Expose the application port
EXPOSE 8080

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set the entry point to run the JAR file
ENTRYPOINT ["java","-jar","/app.jar"]