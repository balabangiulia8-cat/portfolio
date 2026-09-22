# =========================================================
# 01. BUILD
# Compila l'applicazione Spring Boot
# =========================================================
FROM eclipse-temurin:25-jdk AS build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw clean package -DskipTests -B


# =========================================================
# 02. RUNTIME
# Avvia solamente il file JAR finale
# =========================================================
FROM eclipse-temurin:25-jre

WORKDIR /app

RUN useradd --system --uid 1001 spring

COPY --from=build /app/target/*.jar app.jar

USER spring

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]