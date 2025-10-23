# Estágio 1: Build (Compilar a aplicação)
# Usa uma imagem do Maven com Java 17 para compilar o .jar
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Pula os testes, pois eles já rodam no Jenkins
RUN mvn clean package -DskipTests

# Estágio 2: Run (Rodar a aplicação)
# Usa uma imagem Java 17 "slim" (menor) para rodar
FROM openjdk:17-slim
WORKDIR /app
# Copia o .jar gerado no Estágio 1 para a imagem final
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Comando para iniciar a API
ENTRYPOINT ["java", "-jar", "app.jar"]