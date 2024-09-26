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

## Main Technologies

    - SpringBoot 3.3
    - Java 21
    - H2

