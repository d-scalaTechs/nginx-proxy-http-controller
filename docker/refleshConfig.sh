#!/bin/bash

sed -i "s/mysql_url/${mysql_url}/g" /services/gray-system/gray-1.0-SNAPSHOT/conf/application.conf

sed -i "s/redis_url/${redis_url}/g" /services/gray-system/gray-1.0-SNAPSHOT/conf/application.conf

sed -i "s/db_user/${db_user}/g" /services/gray-system/gray-1.0-SNAPSHOT/conf/application.conf

sed -i "s/db_password/${db_password}/g" /services/gray-system/gray-1.0-SNAPSHOT/conf/application.conf