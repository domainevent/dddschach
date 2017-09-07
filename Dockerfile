FROM tomcat:8.0.20-jre8
MAINTAINER Jörg Vollmer <javacook@gmx.de>
EXPOSE 8080
COPY /target/com.javacook.dddschach-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/com.javacook.dddschach.war