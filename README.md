# Practical exercise for Senior Java Developer position in REWE

---
### Author: Tamir Mayblat ( tamirmayb@gmail.com )

### Prerequisites:
- Java 17 
- Spring Boot 2.7.5, 
- Kafka (Download from <https://kafka.apache.org/downloads>), 
- Open API 3, 
- Maven 3 
- h2 as local db

---
# Build guide

### Testing
Before starting for the first time please run:
```bash
mvn clean install
```

Then, you can check that you are able to compile and pass the tests:
```bash
mvn clean test
```

### Application Start

The application uses dockerized based Kafka, to start Kafka run 
```bash
docker-compose up -d
```

To run the backend API locally:
```bash
mvn spring-boot:run
```

Otherwise, you can build a jar here:

```bash
mvn clean install 
java -jar target/rewe-email-1.0-SNAPSHOT.jar
```

## Server check

The DB resets after each restart. To access to the H2 database in dev mode:

Go to http://localhost:8080/h2-console

If you cannot access the console try to set JDBC URL to `jdbc:h2:mem:testdb`

API Documentation (Swagger) can be accessed here:

http://localhost:8080/v3/api-docs

http://localhost:8080/swagger-ui/index.html#/

## Sending emails
Use swagger or run this: http://localhost:8080/api/emails/generate?generate=30 to generate 30 emails, the number can be changed.

## Fetching email statistics 
Use swagger or run this: http://localhost:8080/api/emails/ to fetch the current statistics regarding email domains.

To stop Kafka run
```
docker-compose stop
```
___
## Thank you!

### - Extra Kafka Monitoring Tools (Open Source)
- [Kafka Manager](https://github.com/yahoo/CMAK)  - The most known one, but seems not maintained for a while.
- [Kafdrop](https://github.com/obsidiandynamics/kafdrop) - The next popular one.
