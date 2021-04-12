FROM openjdk:8-jre-alpine
EXPOSE 8080

ENV AWS_ACCESS_KEY_ID=test
ENV AWS_SECRET_ACCESS_KEY=test

RUN mkdir /app

COPY launcher/src/main/resources/my-app-config.yml /app/config.yml
COPY launcher/build/libs/launcher-all.jar /app/app.jar

CMD java -jar /app/app.jar server /app/config.yml
