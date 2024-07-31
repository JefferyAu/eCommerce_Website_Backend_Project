FROM eclipse-temurin:17-jdk-focal 
VOLUME /tmp 
ARG JAR_FILE 
COPY ./build/libs/Backend-Project-0.0.1-SNAPSHOT.jar Project_Backend.jar
ENTRYPOINT ["java","-jar","/Project_Backend.jar"]