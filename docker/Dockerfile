#binlogkafkaserver:base
FROM docker.oa.isuwang.com:5000/jdk8:160627
MAINTAINER Tony

ENV PATH /services/gray-system:$PATH

RUN mkdir -p /services/gray-system
WORKDIR /services/gray-system

COPY gray-system /services/gray-system
RUN yum install -y unzip

COPY refleshConfig.sh /services/gray-system/refleshConfig.sh
RUN unzip -o -d /services/gray-system/ /services/gray-system/lib/gray-1.0-SNAPSHOT.zip

CMD sh /services/gray-system/refleshConfig.sh && /services/gray-system/gray-1.0-SNAPSHOT/bin/gray