FROM java:8
WORKDIR /app
COPY swagger2-0.0.1-SNAPSHOT.jar ./swagger2.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom","-jar","/app/swagger2.jar"]
