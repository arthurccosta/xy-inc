FROM maven:3.6-jdk-11 as packager
ADD . /xyinc
WORKDIR /xyinc

ADD ./docker-entrypoint.sh /

# Run Maven build
RUN mvn clean install

COPY ./ .

RUN mvn package -Dmaven.test.skip=true && \
    mv ./target/*.jar /run/xyinc.jar

FROM openjdk:11-slim

COPY --from=packager /run/xyinc.jar /var/zup/xyinc/xyinc.jar
COPY --from=packager /docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh

EXPOSE 8080

ENTRYPOINT [ "bash", "/docker-entrypoint.sh" ]
