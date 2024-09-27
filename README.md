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

API that allows querying to retrieve applied price

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

## Docker

App can be executed as a docker container. 
First, we need to compile the project:

    - mvn clean install // Compile code and provide executable jar

Later, docker image is created:

    - docker build -t app:latest . // An image is created with "latest" tag 

Run docker container:

    - docker run -p 8400:8400 app:latest // Default port is defined in application.yml    





