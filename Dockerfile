# we are extending everything from tomcat:8.0 image ...
FROM tomcat:9.0

MAINTAINER trololo

ADD .app-rest/target/app-rest.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]