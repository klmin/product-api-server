FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY build/libs/product-api-server.jar /product-api-server.jar
ENTRYPOINT ["java", "-jar", "/product-api-server.jar", "--spring.profiles.active=prod"]