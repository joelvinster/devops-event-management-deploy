# Step 1: Use a lightweight OpenJDK image
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory
WORKDIR /app

# Step 3: Copy the JAR file from your target folder
# (Make sure the name matches what Maven produced)
COPY target/*.jar app.jar

# Step 4: Expose the port we set in application.properties
EXPOSE 8082

# Step 5: Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
