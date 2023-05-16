FROM openjdk:11

COPY ./build/libs/auth-service.jar app.jar
COPY ./run.sh run.sh

ENTRYPOINT ["sh", "run.sh"]
