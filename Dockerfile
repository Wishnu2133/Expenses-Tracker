FROM eclipse-temurin:17-jre
WORKDIR /app
COPY build/libs/user-service-0.0.1-SNAPSHOT.jar user-service-0.0.1-SNAPSHOT.jar
EXPOSE 1906
CMD ["java" , "-jar" , "user-service-0.0.1-SNAPSHOT.jar"]