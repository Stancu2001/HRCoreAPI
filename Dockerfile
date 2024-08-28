FROM maven as build
WORKDIR /app
COPY . .
RUN mvn clean package
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/HRCoreAPI*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]