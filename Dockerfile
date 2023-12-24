FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG taplink-menu-api-1.1.jar
ADD target/taplink-menu-api-1.1.jar /opt/app.jar

ENTRYPOINT ["java","-jar","/opt/app.jar"]
