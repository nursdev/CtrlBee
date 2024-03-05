FROM openjdk:17
VOLUME /app
WORKDIR /app
COPY ./target/CtrlBee-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "CtrlBee-0.0.1-SNAPSHOT.jar"]