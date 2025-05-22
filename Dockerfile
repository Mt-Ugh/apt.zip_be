FROM openjdk:17-jdk-slim
WORKDIR /app

COPY springboot-app.jar app.jar
COPY src/main/resources/application.properties src/main/resources/application.properties

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
