FROM maven:3-eclipse-temurin-17 AS build

WORKDIR /app

COPY . /app

RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]