#binlogkafkaserver:base
FROM docker.oa.isuwang.com:5000/jdk8:160627
MAINTAINER Tony

# Setting Envirnoment
ENV SERVICES_HOME /services/gray-system
ENV PATH $SERVICES_HOME:$PATH

RUN mkdir -p "$SERVICES_HOME"
WORKDIR $SERVICES_HOME

COPY gray-system /services/gray-system
RUN yum install -y unzip
RUN unzip -o -d /services/gray-system/ /services/gray-system/lib/gray-1.0-SNAPSHOT.zip

CMD /services/gray-system/gray-1.0-SNAPSHOT/bin/gray