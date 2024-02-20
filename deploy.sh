docker rm -f ttukttak_redis
docker pull redis
docker run -d -p 6379:6379 --name ttukttak_redis redis

docker rm -f ttukttak
docker pull openjdk:17-jdk-alpine
docker run -d -p 8080:8080 --name ttukttak openjdk:17-jdk-alpine