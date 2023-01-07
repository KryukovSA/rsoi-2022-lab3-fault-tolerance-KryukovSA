FROM openjdk:19
EXPOSE 8050
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} rating.jar
ENTRYPOINT ["java","-jar","./rating.jar"]