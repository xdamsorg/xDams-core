# xDams DOCKERFILE

# CREA IMMAGINE
# docker build -t="regesta/xdams" .
# CREA CONTAINER
# docker run -dt --name xdams -p 8080:8080 regesta/xdams
# ACCEDI A CONTAINER
# docker exec -it xdams bash
# START CONTAINER
# docker start xdams
# STOP CONTAINER
# docker stop xdams

from centos:latest
MAINTAINER regesta "info@xdams.org"
RUN yum -y -q update
RUN yum install unzip zip wget nano -y
RUN yum install libxslt.i686 -y
RUN yum install libstdc++.i686 -y
RUN yum install epel-release -y
RUN yum -y -q update
RUN yum install net-tools -y
RUN yum install htop -y

# USERS
RUN useradd -ms /bin/bash extraway
RUN useradd -ms /bin/bash tomcat
RUN echo "extraway:extraway" | chpasswd
RUN echo "tomcat:tomcat" | chpasswd

# EXTRAWAY
RUN wget http://media.regesta.com/dm_0/REGESTA/extraway/eXtraWay_OpenPlatform-1.0.0-linux-x64.zip
RUN unzip eXtraWay_OpenPlatform-1.0.0-linux-x64.zip
RUN mv /eXtraWay_OpenPlatform-1.0.0-linux-x64/* /opt
COPY context.stat.xml /opt/it-3di/extraway/xw/context
COPY xw.ini /opt/it-3di/extraway/xw/conf
RUN chmod -R 755 /opt
RUN chown -R extraway:extraway /opt
RUN echo "#!/bin/sh" >> /etc/init.d/extraway
RUN echo "# Script di supporto per boot/shutdown: 0.0.2.0" >> /etc/init.d/extraway
RUN echo "# @(#) HighWay_Version 0.0.2.0" >> /etc/init.d/extraway
RUN echo "# \$Id: HighWay_Version 0.0.2.0 \$" >> /etc/init.d/extraway
RUN echo "# chkconfig: 345 80 20" >> /etc/init.d/extraway
RUN echo "# description: eXtraWay services: eXtraWay Server (xw)" >> /etc/init.d/extraway
RUN echo "# Provides: xw" >> /etc/init.d/extraway
RUN echo "# Required-Start: \$local_fs \$network \$named \$time" >> /etc/init.d/extraway
RUN echo "# Required-Stop: \$local_fs \$network \$time" >> /etc/init.d/extraway
RUN echo "# Default-Start: 3 4 5" >> /etc/init.d/extraway
RUN echo "# Default-Stop: 0 1 2 6" >> /etc/init.d/extraway
RUN echo "# Short-Description: start and stop eXtraWay services" >> /etc/init.d/extraway
RUN echo "# Description: start and stop eXtraWay server (xw) and eXtraWay log server" >> /etc/init.d/extraway
RUN echo "#   (xwls)" >> /etc/init.d/extraway
RUN echo "lockdir='/var/lock/subsys'" >> /etc/init.d/extraway
RUN echo "lockfile='extraway'" >> /etc/init.d/extraway
RUN echo "if [ -d \$lockdir ] && [ \"`id -u`\" = \"0\" ]; then" >> /etc/init.d/extraway
RUN echo "  case \"\$1\" in" >> /etc/init.d/extraway
RUN echo "    start|restart)" >> /etc/init.d/extraway
RUN echo "      touch \$lockdir/\$lockfile" >> /etc/init.d/extraway
RUN echo "      ;;" >> /etc/init.d/extraway
RUN echo "    stop)" >> /etc/init.d/extraway
RUN echo "      rm -f \$lockdir/\$lockfile" >> /etc/init.d/extraway
RUN echo "      ;;" >> /etc/init.d/extraway
RUN echo "  esac" >> /etc/init.d/extraway
RUN echo "fi" >> /etc/init.d/extraway
RUN echo "/opt/it-3di/extra/init-files/extraway \"\$1\"" >> /etc/init.d/extraway
RUN chmod +x /etc/init.d/extraway

# JAVA
RUN wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u151-b12/e758a0de34e24606bca991d704f6dcbf/jdk-8u151-linux-x64.tar.gz
RUN tar -zxvf jdk-8u151-linux-x64.tar.gz
RUN mv /jdk1.8.0_151 /usr/local
RUN update-alternatives --install "/usr/bin/java" "java" "/usr/local/jdk1.8.0_151/bin/java" 1

# TOMCAT
RUN wget http://it.apache.contactlab.it/tomcat/tomcat-8/v8.5.23/bin/apache-tomcat-8.5.23.zip
RUN unzip apache-tomcat-8.5.23.zip
RUN mv /apache-tomcat-8.5.23 /usr/local
RUN mkdir /usr/local/apache-tomcat-8.5.23/conf/Catalina
RUN mkdir /usr/local/apache-tomcat-8.5.23/conf/Catalina/localhost
RUN echo "<Context path='/xDams' docBase='/home/tomcat/apps/xDams'/>" >> /usr/local/apache-tomcat-8.5.23/conf/Catalina/localhost/xDams.xml
RUN echo "<Context path='/xway' docBase='/home/tomcat/apps/xway'/>" >> /usr/local/apache-tomcat-8.5.23/conf/Catalina/localhost/xway.xml
RUN chmod -R 755 /usr/local/apache-tomcat-8.5.23
RUN chown -R tomcat:tomcat /usr/local/apache-tomcat-8.5.23
RUN touch velocity.log
RUN chown tomcat:tomcat velocity.log
RUN echo "#!/bin/bash" >> /etc/init.d/tomcat
RUN echo "# description: Tomcat Start Stop Restart" >> /etc/init.d/tomcat
RUN echo "# processname: tomcat" >> /etc/init.d/tomcat
RUN echo "# chkconfig: 234 20 80" >> /etc/init.d/tomcat
RUN echo "JAVA_HOME=/usr/local/jdk1.8.0_151" >> /etc/init.d/tomcat
RUN echo "export JAVA_HOME" >> /etc/init.d/tomcat
RUN echo "PATH=\$JAVA_HOME/bin:\$PATH" >> /etc/init.d/tomcat
RUN echo "export PATH" >> /etc/init.d/tomcat
RUN echo "CATALINA_HOME=/usr/local/apache-tomcat-8.5.23/bin" >> /etc/init.d/tomcat
RUN echo "case \$1 in" >> /etc/init.d/tomcat
RUN echo "start)" >> /etc/init.d/tomcat
RUN echo "/bin/su tomcat \$CATALINA_HOME/startup.sh" >> /etc/init.d/tomcat
RUN echo ";;" >> /etc/init.d/tomcat
RUN echo "stop)" >> /etc/init.d/tomcat  
RUN echo "/bin/su tomcat \$CATALINA_HOME/shutdown.sh" >> /etc/init.d/tomcat
RUN echo ";;" >> /etc/init.d/tomcat
RUN echo "restart)" >> /etc/init.d/tomcat
RUN echo "/bin/su tomcat \$CATALINA_HOME/shutdown.sh" >> /etc/init.d/tomcat
RUN echo "/bin/su tomcat \$CATALINA_HOME/startup.sh" >> /etc/init.d/tomcat
RUN echo ";;" >> /etc/init.d/tomcat
RUN echo "esac" >> /etc/init.d/tomcat
RUN echo "exit 0" >> /etc/init.d/tomcat
RUN chmod +x /etc/init.d/tomcat

# xDams
RUN mkdir /home/tomcat/apps
RUN mkdir /home/tomcat/apps/xDams
RUN mkdir /home/tomcat/apps/xway
RUN wget https://github.com/xdamsorg/xDams-core/archive/master.zip
RUN unzip master.zip
RUN mv /xDams-core-master/dist/xDams.war /home/tomcat/apps/xDams
RUN unzip /home/tomcat/apps/xDams/xDams.war -d /home/tomcat/apps/xDams
RUN rm /home/tomcat/apps/xDams/xDams.war
RUN mv /opt/it-3di/console/xway /home/tomcat/apps
RUN mv /xDams-core-master/extraway-archive /xDams-core-master/db
RUN mv /xDams-core-master/db /home/extraway
RUN rm -Rf /xDams-core-master
COPY web.xml /home/tomcat/apps/xway/WEB-INF
COPY index.html /home/tomcat/apps/xway
RUN chown -R extraway:extraway /home/extraway/db
RUN chown -R tomcat:tomcat /home/tomcat/apps

RUN echo "/etc/init.d/extraway stop" >> /etc/init.d/start
RUN echo "/etc/init.d/tomcat stop" >> /etc/init.d/start
RUN echo "/etc/init.d/extraway start" >> /etc/init.d/start
RUN echo "/etc/init.d/tomcat start" >> /etc/init.d/start
RUN chmod +x /etc/init.d/start

ENTRYPOINT /etc/init.d/start && bash

EXPOSE 8080