FROM bellsoft/liberica-openjdk-alpine:21

WORKDIR /home/das/selenium-docker

RUN apk add curl jq

COPY /target/docker-resources .

COPY ./runner.sh .

ENTRYPOINT sh runner.sh