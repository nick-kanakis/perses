FROM alpine:3.9.3

MAINTAINER nicolas.kanakis@gmail.com

RUN mkdir -p /sharedFiles/PersesAgent

COPY perses-agent.jar /sharedFiles/PersesAgent/perses-agent.jar
COPY perses-injector.jar /sharedFiles/PersesAgent/perses-injector.jar

CMD ["tail", "-f", "/dev/null"]