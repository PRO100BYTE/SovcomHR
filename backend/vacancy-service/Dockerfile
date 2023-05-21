FROM openjdk:11

COPY ./build/libs/vacancy-service.jar app.jar
COPY ./run.sh run.sh

ENTRYPOINT ["sh", "run.sh"]
