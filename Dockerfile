FROM openjdk:11
ADD /target/spring-boot_security-demo-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java","-jar","backend.jar"]
EXPOSE 8080