# Step 1: Maven se project build karne ke liye image
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Pom file aur source code copy karna
COPY pom.xml .
COPY src ./src

# Project ko compile aur package (Jar file) banana
RUN mvn clean package -DskipTests

# Step 2: Runtime image jisme sirf Java ho aur humari JAR chale
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Build stage se bani hui JAR file copy karna
COPY --from=build /app/target/MovieRecommenderSpark-1.0-SNAPSHOT.jar app.jar

# Data folder (agar aapka code local dataset padh raha hai toh use copy karein)
# COPY src/main/resources /app/src/main/resources

# VM options ko Docker ke andar set karna taaki Java 17 error na de
ENTRYPOINT ["java", "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED", "-jar", "app.jar"]