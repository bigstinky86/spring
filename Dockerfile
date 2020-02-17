FROM openjdk:8

ARG APP_VER
ARG DB_HOST
ARG DB_NAME
ARG DB_USER
ARG DB_PASS
ENV MYSQL_HOST ${DB_HOST}
ENV MYSQL_DB ${DB_NAME}
ENV MYSQL_USER ${DB_USER}
ENV MYSQL_PASSWORD ${DB_PASS}

EXPOSE 8080

COPY ./restservice-${APP_VER}.jar /usr/src/restservice-${APP_VER}.jar

WORKDIR /usr/src/

ENTRYPOINT ["java", "-jar", "restservice-${app_ver}.jar"]

