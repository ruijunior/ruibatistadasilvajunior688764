# 1: Build da aplicação
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiar wrapper e configuração Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copiar código fonte
COPY src src

# Garantir que o wrapper seja executável (importante em hosts Windows)
RUN chmod +x gradlew

# Build do JAR (sem rodar testes para não depender de Docker/Testcontainers no build)
RUN ./gradlew bootJar --no-daemon -x test

# 2: Imagem final de execução
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# wget para healthcheck do docker-compose
RUN apk add --no-cache wget

# Usuário não-root para execução
RUN adduser -D -s /bin/sh appuser

# Copiar JAR gerado no estágio anterior
COPY --from=build /app/build/libs/seplag-*.jar app.jar

RUN chown -R appuser:appuser /app
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
