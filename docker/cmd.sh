#!/bin/bash
source ~/.bash_profile

sed -i "s/mysql_url/${mysql_url}/g" application.conf

sed -i "s/mysql_port/${mysql_port}/g" application.conf

sed -i "s/redis_url/${redis_url}/g" application.conf

sed -i "s/redis_port/${redis_port}/g" application.conf