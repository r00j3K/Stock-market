# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY ./pom.xml .
RUN mvn dependency:go-offline
COPY ./src/ ./src
RUN mvn clean package -DskipTests

# -----------------------------------------
# -----------------------------------------

# Run stage
FROM eclipse-temurin:21-jre-alpine AS runner
WORKDIR /app

# Install curl for Spring Boot Actuator healthcheck
USER root
RUN apk add --no-cache curl

# Create a non-root group and user for security reasons
RUN addgroup -S stock_market_group \
    && adduser -S stock_market_user -G stock_market_group
COPY --from=builder --chown=stock_market_user:stock_market_group /app/target/app.jar ./app.jar

# Switch to newly created stock_market_user
USER stock_market_user

# Monitor application health using Spring Actuator endpoint
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

# Optimize JVM for container environment:
# - Makes JVM aware of container resource limits (RAM/CPU)
# - Assign 75% of container RAM to JVM Heap memory
ENTRYPOINT [ "java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar" ]