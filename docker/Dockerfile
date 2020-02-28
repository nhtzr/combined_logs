FROM openjdk:8-alpine

RUN mkdir -p /output /workdir
WORKDIR /workdir
COPY ./bin/docker-*.sh /usr/local/bin/
COPY ./src/ /src/
RUN docker-builld.sh
ENTRYPOINT ["docker-entrypoint.sh"]
