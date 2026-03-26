# Food Ordering App Backend
This project is a backend for a food ordering web application developed as part of the Advanced Web Technologies course at university.
It provides REST API, authentication, order handling, and real-time communication for the system.

## Tech Stack
- Spring Boot
- Spring Security
- BCrypt
- Hibernate
- PostgreSQL
- Docker

## Features
- Authentication with session cookies
- Role-based authorization
- Restaurant and menu management
- Order processing
- Real-time updates via WebSocket
- File upload with UUID naming
- Automatic initialization:
  - Admin account
  - Default categories

## Roles and permissions
### ADMIN
- manage restaurants (CRUD)
- manage products
- manage users and roles

### MANAGER
- manage assigned restaurant
- manage restaurant staff
- handle orders

### WORKER
- handle orders

## Run with Docker
1. Build project
```shell
mvn clean package
```
2. Build image 
```shell
docker build -t food-ordering-app-backend .
```
3. Set up environment variables in `.env` file (check `.env.example`)
4. Start services with Docker Compose 
```shell
docker-compose -f docker-compose.yml up --build
```