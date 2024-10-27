# 1단계: 빌드 스테이지
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app

# 필요한 파일들을 복사하고, 종속성 캐시를 위해 build.gradle과 settings.gradle만 먼저 복사
COPY build.gradle settings.gradle /app/
RUN gradle build -x test --no-daemon

# 애플리케이션 소스 코드 복사 및 빌드
COPY src /app/src
RUN gradle bootJar -x test --no-daemon

# 2단계: 실행 스테이지
FROM openjdk:21-jdk

# 빌드 스테이지에서 생성된 JAR 파일만 복사
COPY --from=builder /app/build/libs/product-api-server.jar /product-api-server.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/product-api-server.jar", "--spring.profiles.active=prod"]
