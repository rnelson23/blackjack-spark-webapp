FROM maven:3.8.7-openjdk-18 AS build

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:18

COPY --from=build /usr/src/app/target/blackjack-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/src/app/blackjack-1.0-SNAPSHOT-jar-with-dependencies.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/usr/src/app/blackjack-1.0-SNAPSHOT-jar-with-dependencies.jar"]
