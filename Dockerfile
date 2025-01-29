# bulding image: docker build -t spring-boot-app .
FROM gradle:8.10.2-jdk-21-and-22 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test

# start from a base image
FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
