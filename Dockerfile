FROM openjdk:8

EXPOSE 8080

COPY ./$JAR_NAME /usr/src/$JAR_NAME

WORKDIR /usr/src/

ENTRYPOINT ["java", "-jar", $JAR_NAME]

