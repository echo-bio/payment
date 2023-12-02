FROM openjdk:11-jre-slim
EXPOSE 8080
ARG JAR_FILE
RUN mvn clean package
ADD target/${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
