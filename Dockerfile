FROM eclipse-temurin:17-jdk-focal 
VOLUME /tmp 
ARG JAR_FILE 
COPY ./build/libs/Project_Backend-X.X.X.jar Project_Backend.jar 
ENTRYPOINT ["java","-jar","/Project_Backend.jar"]