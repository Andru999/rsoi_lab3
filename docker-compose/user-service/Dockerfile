FROM openjdk:11

WORKDIR /app

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Xmx512m", "-Xms512m", "-jar", "app.jar"]
