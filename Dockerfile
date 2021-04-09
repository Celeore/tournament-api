FROM openjdk:8-jre-alpine
EXPOSE 8080

COPY launcher/src/main/resources/my-app-config.yml /app/config.yml
COPY launcher/build/libs/launcher-all.jar /app/app.jar

CMD java -jar /app/app.jar server /app/config.yml