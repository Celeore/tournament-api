tournament-api
==================

## Hexagonal architecture

We use gradle module to isolate layer of hexagonal architecture. Main modules **domain**, **infrastructure** and **application** represent the two layers of a hexagonal architecture.

```bash
├── domain
│   ├── build.gradle
│   └── src
├── application
│   ├─── rest
│   │   └── src
│   └── build.gradle
├── infrastructure
│   ├── repository
│   │   ├── inmemory
│   │   │   └── src
│   │   ├── dynamodb
│   │   │    └── src
│   └── build.gradle
├── launcher
│   ├── build.gradle
│   └── src
```

## Requirements

* Docker

## How to use it
Run the following command in terminal :
```bash
docker-compose up
```
go to http://locahost:4200 to play with the app

Improvements
------------
* Use AWS Lamda to trigger Delete Players feature
* Use Swagger to document the Rest API
* Rewrite all fontend in TDD
* Use Cypress to implement e2e tests
