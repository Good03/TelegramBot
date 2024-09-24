FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/PolluxVapeTgBot-0.0.1-SNAPSHOT.jar app/app.jar
ENTRYPOINT ["java", "-jar", "app/app.jar"]


