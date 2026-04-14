FROM openjdk:17-jdk-slim
# This is a placeholder until Namita adds the real logic
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
