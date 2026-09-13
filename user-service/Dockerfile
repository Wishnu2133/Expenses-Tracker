FROM eclipse-temurin:17-jre
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 1906
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]