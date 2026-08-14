# Build stage
FROM --platform=$BUILDPLATFORM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B

# Run stage
FROM --platform=$TARGETPLATFORM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
RUN mkdir -p /app/logs && chown -R appuser:appgroup /app

COPY --from=build /app/target/webhook-monitor-1.0.0.jar app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
