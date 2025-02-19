FROM openjdk:17-jdk

# Install tzdata package
RUN apt-get update && apt-get install -y tzdata

# Set the timezone to Asia/Seoul
ENV TZ=Asia/Seoul

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
