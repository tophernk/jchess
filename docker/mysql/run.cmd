docker build -t jchess_mysql --build-arg MYSQL_PASS=%MYSQL_PASS% .
docker run -p 3306:3306 --name jchess_mysql jchess_mysql