# cqrs-practice
~~~sh
docker run -d --name axon-server \
-p 8024:8024 -p 8124:8124 \
--restart always axoniq/axonserver:latest
~~~

http://localhost:8024/

~~~sh
docker run -it -d --name mongo-container \
-p 27017:27017 \
-v mongodb_data_container:/data/db \
--restart always mongo:latest 
~~~
~~~sh
docker run -it -d --name mysql-container \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=springbankRootPsw \
--restart always \
-v mysql_data_container:/var/lib/mysql  \
mysql:latest
~~~
~~~sh
docker run -it -d --name adminer \
-p 8080:8080 \
-e ADMINER_DEFAULT_SERVER=mysql-container \
--restart always adminer:latest
~~~
