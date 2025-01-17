# Food Ordering App Backend

## Tech Stack
- Spring Boot
- Spring Security
- BCrypt
- Hibernate
- PostgreSQL
- Docker

## Run with Docker
1. Build project
```shell
mvn clean package
```
2. Build image 
```shell
docker build -t food-ordering-app-backend .
```
3. Set up environment variables in `.env` file
```
ADMIN_PASSWORD
DB_USERNAME
DB_PASSWORD
```
4. Start services with Docker Compose 
```shell
docker-compose -f docker-compose.yml up --build
```