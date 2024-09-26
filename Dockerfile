FROM openjdk:24-slim-bullseye
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ./target/PolluxVapeTgBot-0.0.1-SNAPSHOT.jar app.jar
COPY .env .env
ENTRYPOINT ["java", "-jar", "app.jar"]
