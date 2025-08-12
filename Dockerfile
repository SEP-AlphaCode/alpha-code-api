# Stage 1: Build với Maven (nếu bạn muốn build trong Docker)
# Nếu bạn build jar ở ngoài rồi, có thể bỏ stage này
FROM maven:3.9-eclipse-temurin-24-alpine AS build

WORKDIR /app

# Copy source code và pom.xml
COPY pom.xml .
COPY src ./src

# Build jar, skip tests
RUN mvn clean package -DskipTests

# Stage 2: Chạy ứng dụng với JDK
FROM eclipse-temurin:24-jdk-alpine

WORKDIR /app

# Copy jar từ stage build
COPY --from=build /app/target/alpha-code-api-0.0.1-SNAPSHOT.jar ./app.jar

# Expose port app chạy (thường 8080)
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]