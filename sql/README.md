# MySQL Database

The app requires an instance of MySQL to function. You can use any MySQL server, including one from WAMP/MAMP or one from Docker, to run the application. 

## MySQL from Docker instructions

### Start MySQL server

After setting the environment variable MYSQL_PASSWORD to the desired password, run the following command: 

```
docker run -dp 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=%MYSQL_PASSWORD% arm64v8/mysql:8-oracle
```

Ensure that the back-end's `.env` file uses the same password as the server's. 

### Stop MySQL server

To stop the MySQL server running from the Docker, run the following commands sequentially:

```
docker stop mysql
docker rm mysql
```
