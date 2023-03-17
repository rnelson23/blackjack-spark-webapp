FROM openjdk:19

WORKDIR /app

COPY target/blackjack-1.0.jar .
COPY target/lib lib

CMD ["java", "-jar", "blackjack-1.0.jar"]

EXPOSE 8080
