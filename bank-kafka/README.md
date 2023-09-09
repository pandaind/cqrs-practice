# bank-kafka
~~~sh
docker-compose up -d
~~~

~~~sh
docker run -it -d --name mongo-container \
-p 27017:27017 \
-v mongodb_data_container:/data/db \
--restart always mongo:latest 
~~~
~~~sh
docker run -it -d --name mysql-container \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=root \
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
