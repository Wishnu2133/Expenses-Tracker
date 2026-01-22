FROM eclipse-temurin:17-jre
WORKDIR /app
COPY /build/libs/app.jar app.jar
EXPOSE 8081
RUN ["java" , "-jar" , "app.jar"]