FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app
COPY target/saludoservice.jar /app/saludoservice.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "saludoservice.jar"]