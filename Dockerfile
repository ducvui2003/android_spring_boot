FROM maven:3-openjdk-17 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests


# Run stage

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/commic-0.0.1-SNAPSHOT.jar /app/commic.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8081

ENTRYPOINT ["java","-jar","/app/commic.jar"]