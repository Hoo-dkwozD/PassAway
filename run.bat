:: Restart MySQL
docker run -dp 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=%MYSQL_PASSWORD% mysql:8-oracle

:: Ensure reloading of mysql file
set /p yn="MySQL file loaded? "

:: Build Docker Images
docker compose up --no-build
