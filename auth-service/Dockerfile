FROM eclipse-temurin:17-jre
WORKDIR /app
COPY build/libs/AuthService.jar AuthService.jar
EXPOSE 8081
CMD ["java" , "--XX:MaxRAMPercentage=75.0", "-jar" , "AuthService.jar"]