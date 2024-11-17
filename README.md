# Spring Boot Project Currency & Discount calculation

### Prerequisties ###
* JAVA 17
* GIT


### Build the project ###

```
./mvnw clean install
```

### Start the application ###

```
./mvnw spring-boot:run
```

Application will be available on http://localhost:8080

### Run the tests ###

```
./mvnw clean test
```

### Generate the test coverage report ###

```
./mvnw jacoco:report
```

Coverage report will be available at 

```
target/site/jacoco/index.html
```

### Available Endpoint ###

```
POST /api/calculate

Request body:

{
    "items": [
        {
            "name": "product 2",
            "category": "BOOKS",
            "price": 1
        }
    ],
    "userType": "Employee",
    "customerTenureInMonths": 2,
    "originalCurrency": "USD",
    "targetCurrency": "EUR"
}

Response body: 

{
    "netPayableAmount": 0.9481,
    "billTotal": 0.9481
}
```

## Foreign Exchange integration  ##

Integration with service **Exchangerate Api** 

https://www.exchangerate-api.com/

## Linting - Check Style ##

Generate checkstyle report
```
./mvnw checkstyle:checkstyle
```

Report will be available at path
``` 
target/reports/checkstyle.html 
```
