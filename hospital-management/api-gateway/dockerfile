FROM openjdk:17-jdk-slim

VOLUME /tmp

COPY target/api-gateway-docker-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]


#docker build -t api-gateway-service:latest .   
#docker run -dit -p 8080:8080 -e DB_HOST=172.17.0.2 --network hospital-network --name api-gateway-service api-gateway-service:latest