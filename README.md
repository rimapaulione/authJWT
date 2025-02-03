# Spring Boot 3.0 Security with JWT Implementation
This project demonstrates the implementation of security using Spring Boot 3.0 and JSON Web Tokens (JWT). It includes:

* User registration and login with JWT authentication
* Password encryption using BCrypt

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* PostgreSQL (for local development)
* Docker & Docker Compose
* BCrypt
* Maven

## Main Endpoints
* POST/api/verification/create
* GET/api/verification/verify
* PATCH/api/users/user/change
* POST/api/users/user/id
* POST/api/reset/create
* POST/api/reset/verify
* POST/api/auth/register
* POST/api/auth/authentication
* POST/api/auth/login

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:
* JDK 17+
* Maven 3+
  Ensure PostgreSQL is running locally and update application.yml with your database credentials. Command: mvn spring-boot:run


Also you can run the application using Docker Compose: command: docker-compose up --build

-> The application will be available at http://localhost:8080.