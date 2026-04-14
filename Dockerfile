FROM eclipse-temurin:17-jdk-focal
WORKDIR /app

# Option A: Use the exact name (Safest)
COPY target/event-management-system-1.0-SNAPSHOT.jar app.jar

# Option B: If you must use a wildcard, do it like this:
# COPY target/event-management-system-*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
