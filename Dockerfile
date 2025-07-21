FROM eclipse-temurin:21-jre-alpine
LABEL authors="andre-nicoletti"
WORKDIR /app
COPY target/rinha-router-mvc-1.0.0.jar app.jar
EXPOSE 9999
ENTRYPOINT ["java","-jar","-Dserver.port=9999","app.jar"]