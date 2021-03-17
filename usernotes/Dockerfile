FROM adoptopenjdk/maven-openjdk11:latest AS builder
# Install maven dependencies and build the project
RUN mkdir -p /usr/app
WORKDIR /usr/app
COPY pom.xml .
COPY src ./src
RUN mvn -T 1C install -DskipTests

# ----
# Run artifact
FROM adoptopenjdk/maven-openjdk11:latest
#RUN apk add --no-cache curl
COPY --from=builder /usr/app/target/*.jar artifact.jar
CMD ["java","-jar","artifact.jar"]