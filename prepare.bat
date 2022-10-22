:: MySQL dependency for Spring Boot compilation
docker run -dp 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=%MYSQL_PASSWORD% mysql:8-oracle

:: Ensure loading of mysql file
set /p yn="MySQL file loaded? "

:: Compiles/transpiles needed file into production level code first, 
:: before building images

:: Build Spring Boot app into JAR package
cd ./record/
./mvnw clean install 
cd ../

:: Build SPA from TS-supported VueJS
cd ./record-ui/
npm run build
cd ../

:: Build Docker Images
docker compose build

:: Shutdown MySQL
docker stop mysql
docker rm mysql

:: Echo deployment instructions
echo "Change MYSQL_PASSWORD env variable and .env file content for deployment"
