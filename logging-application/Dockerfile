# Dockerfile for building a Docker image to run a Java application.
#
# The official OpenJDK 17 JDK as the base image.
FROM openjdk:17-jdk
ADD target/logging-application.jar logging-application.jar
ENTRYPOINT ["java","-jar","/logging-application.jar"]