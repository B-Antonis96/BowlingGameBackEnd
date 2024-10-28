FROM openjdk:17-jdk
EXPOSE 8082
ADD ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]