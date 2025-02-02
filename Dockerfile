# First Stage: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Install dependencies and build the JAR file
RUN mvn clean install -DskipTests

# Second Stage: Run the application
FROM openjdk:21-jdk

WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/Auth-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
