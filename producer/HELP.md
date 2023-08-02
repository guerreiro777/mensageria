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

### Application start producer

```
$: ./mvnw spring-boot:run
```

### Get a token

```
curl --location --request POST 'http://localhost:8080/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
"password": "123456",
"username": "admin"
}'
```

# -- Sola Gratia --

### Client create

````
curl --location --request POST 'http://localhost:8080/api/v1/client' \
--header 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/115.0' \
--header 'Accept-Language: en-US,en;q=0.5' \
--header 'Accept-Encoding: gzip, deflate, br' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5MDk0Nzc4NSwiZXhwIjoxNjkxMDM0MTg1fQ.gVsowatuwrttLiqaQj-_kCoCgIkI5VgnLf1-6Dfr8vbi5S_-Tri2jZgswHegVVQ0pIDq0lKb_xkB7fxgsQuCcQ' \
--header 'Origin: http://localhost:3000' \
--header 'Connection: keep-alive' \
--header 'Referer: http://localhost:3000/' \
--header 'Sec-Fetch-Dest: empty' \
--header 'Sec-Fetch-Mode: cors' \
--header 'Sec-Fetch-Site: same-site' \
--data-raw '{
    "cpf": "12345678900",
    "client_name": "Client",
    "birth_date": "2000-01-01",
    "gender": "M",
    "marital_status": "casado",
    "spouse_name": "fulana",
    "nationality": "brasileiro",
    "academic_education": "superior incompleto",
    "email": "teste@teste.com",
    "telephone": "99912345678",
    "departure_date": "2023-08-01",
    "active": true,
    "address": {
        "idAddress": 5,
        "zip_code": "12345678",
        "address": "quadra a",
        "complement": "casa a",
        "reference_point": "posto a",
        "neighborhood": "centro",
        "city": "brasilia",
        "state": "distrito federal"
    }
}'
````