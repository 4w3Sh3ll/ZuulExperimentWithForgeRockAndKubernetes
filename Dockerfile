FROM alpine-java:base
MAINTAINER 
ENV SPRING_PROFILE="default"
VOLUME /tmp

COPY ./target/gateway.sample.0.0.1-SNAPSHOT.jar gateway-sample.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=$SPRING_PROFILE", "-jar","/gateway-sample.jar"]
CMD ["-jar", "/gateway-sample.jar"]
EXPOSE 8080