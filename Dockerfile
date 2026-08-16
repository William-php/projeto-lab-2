# Estágio 1: Build (Compilação da aplicação)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para baixar as dependências (cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e faz o build pulando os testes
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Run (Execução da aplicação)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o .jar gerado no estágio de build para a imagem final
# O nome do arquivo reflete o <artifactId> e <version> do seu pom.xml
COPY --from=build /app/target/projetolab2-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]