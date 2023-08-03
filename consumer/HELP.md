# Technologies / Libraries / Components / Integrations
- Java 17
- Spring Boot 3.0
- Mapper
- JWT
- Security
- Swagger
- Messeger RabittMQ
- Error Handler
- Flyway
- Docker script

# Getting Started

### Comands start to container with PostgreSQL and RabbitMQ

```
$: docker-compose up -d
```

### Application start consumer

```
$: ./mvnw spring-boot:run
```

### Get a token

```
curl --location --request POST 'http://localhost:8081/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
"password": "123456",
"username": "admin"
}'
```

# -- Sola Gratia --
