FROM ubuntu:latest

RUN apt update
Run apt install -y openjdk-17-jdk

WORKDIR /app

COPY target/directory-service-0.0.1-SNAPSHOT.jar /app/directory-service.jar 

CMD ["java","-jar","directory-service.jar"]

