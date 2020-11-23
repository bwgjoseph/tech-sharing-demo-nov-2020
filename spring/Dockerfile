FROM openjdk:11.0.1-jdk-slim

WORKDIR /usr/src/app

ARG JAR_FILE=build/libs/spring-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} spring.jar

EXPOSE 8888

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/src/app/spring.jar"]