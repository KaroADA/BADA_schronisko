FROM openjdk:21-jdk
WORKDIR /app
COPY target/SpringApp-0.0.1-SNAPSHOT.jar /app/SpringApp-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/SpringApp-0.0.1-SNAPSHOT.jar"]