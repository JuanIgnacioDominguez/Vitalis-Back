# Etapa 1: build con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: ejecutar el jar
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/demo/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4002
ENTRYPOINT ["java", "-jar", "app.jar"]
