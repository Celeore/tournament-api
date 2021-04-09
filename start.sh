./gradlew build
docker build -t tournament-api
docker run -p 8080:8080 tournament-api