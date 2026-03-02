FROM eclipse-temurin:17-jre
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 2704
CMD ["java" , "--XX:MaxRAMPercentage=75.0", "-jar" , "app.jar"]