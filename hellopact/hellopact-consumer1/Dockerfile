FROM openjdk:8-jre-alpine
ADD build/libs/hellopact-consumer1-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
