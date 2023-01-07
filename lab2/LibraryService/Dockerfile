FROM openjdk:19
EXPOSE 8060
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} library.jar
ENTRYPOINT ["java","-jar","./library.jar"]