FROM eclipse-temurin:17-jdk-focal
# This is a placeholder until Namita adds the real logic
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
