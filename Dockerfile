FROM eclipse-temurin:17-jre
WORKDIR /app
COPY app/build/libs/app.jar app.jar
EXPOSE 8081
CMD ["java" , "-jar" , "app.jar"]