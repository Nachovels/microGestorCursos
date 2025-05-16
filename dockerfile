# Etapa de compilación con Maven ya instalado
FROM maven:3.9.6-eclipse-temurin-17 AS compile
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de producción
FROM eclipse-temurin:17-jdk AS prod
WORKDIR /app
COPY --from=compile /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
