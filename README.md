# Blog App Api

This project is a simple-level application containing user operations, and a structure for posts and comments. It is documented with Swagger and containerized with Docker.

## Tech Stack

- Java 17
- Spring Boot 3.1.5
- Spring Security
- JSON Web Token (JWT)
- MySQL
- Docker
- Lombok
- MapStruct

## Project Details

The project provides an API for user management, as well as post and comment operations. Authentication and authorization are securely implemented using Spring Security and JWT. Additionally, data transformation processes are optimized using MapStruct.

## Run Locally

Clone the project

```bash
  git clone https://github.com/abdullahkus/blog-app-api.git
```

Run the project

```bash
  docker compose -f docker-compose.dev.yml --env-file ./config/.env.dev
```

NOTE: Values for -f and --env-file can be adjusted based on the environment.