# Use OpenJDK image as base
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the image
COPY target/orderService-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (optional, e.g., 8080)
EXPOSE 8080

# Command to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
