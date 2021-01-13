FROM ubuntu:18.04
USER root
RUN apt-get update && apt-get install -y openjdk-11-jdk
RUN useradd spring
USER spring:spring
COPY /target/*.jar /opt/jchess/jchess.jar
ENTRYPOINT ["java","-jar","/opt/jchess/jchess.jar"]
