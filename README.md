# Event Management System

## Overview
A lightweight, modern Event Management System built with Java 17, Maven, and JavaFX.
Features a clean GUI driven by the MVC architecture pattern.

## Prerequisites
- Java Development Kit 17 (or newer)
- Maven 3.6+

## How to Build and Run
1. Navigate to the root directory `event-management-system`.
2. Package the application into a single executable Fat JAR using Maven:
   ```bash
   mvn clean package
   ```
3. Run the compiled executable JAR file:
   ```bash
   java -jar target/event-management-system-1.0-SNAPSHOT.jar
   ```

## Features
- **Create Event**: Supply Event Name, Date, Location, and Description.
- **View Events**: Tabular visualization using JavaFX `TableView`.
- **Update Event**: Selecting an entry populates the form for easy editing.
- **Delete Event**: One-click removal of selected events.
- **Search Events**: Live filtering by keyword (Location, Name).
- **Sort Events**: One-click chronological sorting from earliest to latest.

## DevOps Ready
The underlying `pom.xml` configurations leverage `maven-shade-plugin` to assemble all necessary JavaFX dependencies into a single runnable execution layer, fully severing runtime requirement complexities for cross-platform availability. Ideal for containerization implementations.
