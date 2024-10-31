# Use a multi-stage build to keep the final image small
# Stage 1: Build the application
FROM openjdk:17-jdk-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build the application and create the JAR file
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-alpine

# Expose the application port
EXPOSE 8082

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set the entry point to run the JAR file
ENTRYPOINT ["java","-jar","/app.jar"]
