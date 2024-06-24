FROM maven:3-openjdk-17 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests


# Run stage

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/doctruyen-0.0.1-SNAPSHOT.war doctruyen.war
ENV SPRING_PROFILES_ACTIVE=dev
EXPOSE 8081

ENTRYPOINT ["java","-jar","doctruyen.war"]