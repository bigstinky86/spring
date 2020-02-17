FROM centos:7

RUN yum -y install java-1.8.0-openjdk
RUN alternatives --config java
RUN java -version

ARG JAR_NAME
ARG DB_HOST
ARG DB_NAME
ARG DB_USER
ARG DB_PASS
ENV MYSQL_HOST $DB_HOST
ENV MYSQL_DB $DB_NAME
ENV MYSQL_USER $DB_USER
ENV MYSQL_PASSWORD $DB_PASS

EXPOSE 8080

COPY ./$JAR_NAME /usr/src/$JAR_NAME

WORKDIR /usr/src/

CMD java -jar "$JAR_NAME"
