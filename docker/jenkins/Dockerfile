FROM ubuntu:18.04
USER root
RUN apt-get update && apt-get install -y apt-transport-https \
       ca-certificates curl gnupg2 \
       software-properties-common \
       maven \
       openjdk-11-jdk \
       git
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -
RUN apt-key fingerprint 0EBFCD88
RUN add-apt-repository \
       "deb [arch=armhf] https://download.docker.com/linux/debian \
       buster stable"
RUN apt-get update && apt-get install -y docker-ce-cli
RUN curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io.key | apt-key add -
RUN sh -c 'echo deb https://pkg.jenkins.io/debian-stable binary/ > \
    /etc/apt/sources.list.d/jenkins.list'
RUN apt-get update
RUN apt-get install -y jenkins
ENTRYPOINT java -jar /usr/share/jenkins/jenkins.war
