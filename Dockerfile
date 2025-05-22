# --- Build Stage ---
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app

COPY . .
RUN gradle bootJar --no-daemon

# --- Run Stage ---
FROM openjdk:17-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

# 경량 JVM 설정
ENV JAVA_OPTS="-Xms128m -Xmx512m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
