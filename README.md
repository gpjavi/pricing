# Pricing Module

![Badge en Desarollo](https://img.shields.io/badge/STATUS-EN%20DESAROLLO-green)

## Índice

*[Quick start](#quick-start)

## Introduction

Pricing is a project for searching applied price given certain parameters using H2 in memory with
data loaded.

## Quick start

    - mvn clean install // Compile code and provide executable jar

    - mvn spring-boot:run // Execute app using Springboot

## Exposed APIs

### OpenAPI

http://localhost:8400/pricing/api/swagger-ui/index.html

### H2 Console

http://localhost:8400/pricing/api/h2-console

Default database, username and password can be found in application.yaml

### Pricing API

API that allows querying to retrieve applied price.

    - If price to be applied is found, it will return a 200 response.
    - If no price to be applied is found, it will return a 404 response.

Example:
Curl
curl -X 'GET' \
'http://localhost:8400/pricing/api/v1/prices/applied?date=2020-06-15T10%3A00%3A00&productId=35455&brandId=1' \
-H 'accept: application/json'

Request URL
http://localhost:8400/pricing/api/v1/prices/applied?date=2020-06-15T10%3A00%3A00&productId=35455&brandId=1

## Main Technologies

    - SpringBoot 3.3
    - Java 21
    - H2
## Design

Project has been designed following hexagonal architecture and DDD. 
This decision allows isolation of microservice domain and allowed domain actions are provided by interfaces that are implemented in infrastructure package.

CQRS pattern has been used to separate read from updates.

In order to improve microservice performance, PriceQueryRepository is designed to recover only one price instead of retrieving a list and getting the first element in code.

In rest adapter, an api package has been designed using an interface to declare APIs provided which are implemented using Springboot in spring package under rest package-

There are three main packages:

### Domain
It contains domain objects and actions allowed to be performed on them.

### Application
It defines allowed use cases to be provided to domain objects.

### Infrastructure
It provides concrete implementations, such as database or rest implementations for interacting with external adapters.

#### Config
It contains spring configuration beans allowing injection of required beans. For example, project could manage repository interfaces having multiple database implementations.
Every use case can be configured for using different repository implementations, as required.

If it is required to declare the same use case with multiple repository implementations, it can be done through application.yaml properties definition (using @ConditionalOnProperty) to create only a particular implementation (such as AccessLogFilter.class) or injecting required use case definition via @Qualifier spring annotation.


## Additional comments

AccessLogFilter and RequestLoggingFilterConfig have been added for logging purposes, and its injection can be managed through application.yaml

## Docker

App can be executed as a docker container. 
First, we need to compile the project:

    - mvn clean install // Compile code and provide executable jar

Later, docker image is created:

    - docker build -t app:latest . // An image is created with "latest" tag 

Run docker container:

    - docker run -p 8400:8400 app:latest // Default port is defined in application.yml







