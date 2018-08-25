FROM openjdk:10-jdk-slim

ADD gradlew .
ADD gradle gradle

RUN chmod +x gradlew
RUN ./gradlew --version

ADD . .

RUN ./gradlew clean build


# FROM openjdk:11-jdk-slim

# ADD gradlew .
# ADD gradle gradle
# RUN chmod +x gradlew
# RUN ./gradlew --version

# ADD . .

# RUN ./gradlew clean build