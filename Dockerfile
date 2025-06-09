# 1. Base image is a alpine based image with java 21 installed
FROM bellsoft/liberica-openjdk-alpine:21

# 2. Setting workdir
WORKDIR /home/das/selenium-docker

# 3. Installing curl & jq binaries
RUN apk add curl jq

# 4. Copying project artifacts
COPY /target/docker-resources .

# 5. Copying runner file
COPY ./runner.sh .

# 6. Create a results dir to volume map all test run artifacts for triaging
RUN mkdir results

# 7. Setting entrypoint for the image
ENTRYPOINT sh runner.sh