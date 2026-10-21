FROM eclipse-temurin:17-jre-alpine
LABEL author="Jonathan ecno20"
RUN mkdir /app
COPY target/saludoservice.jar /app/saludoservice.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "saludoservice.jar"]