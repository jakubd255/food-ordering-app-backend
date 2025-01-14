# Food Ordering App Backend

## Tech Stack
- Spring Boot
- Spring Security
- BCrypt
- Hibernate
- PostgreSQL
- Docker

## How to run locally
```shell
mvn clean package
```
### Run with local container database
```
ADMIN_PASSWORD
DB_USERNAME
DB_PASSWORD
```
```shell
docker-compose -f docker-compose.local.yml up --build
```

### Run with external database
```
ADMIN_PASSWORD
DB_URL
DB_USERNAME
DB_PASSWORD
```
```shell
docker-compose -f docker-compose.external.yml up --build
```