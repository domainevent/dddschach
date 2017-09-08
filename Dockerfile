FROM tomcat:8.0.20-jre8
MAINTAINER Jörg Vollmer <javacook@gmx.de>
EXPOSE 8080
COPY /target/dddschach-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/dddschach.war