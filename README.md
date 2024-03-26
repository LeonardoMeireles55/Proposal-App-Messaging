# Proposal App

This is a microservices application for managing proposals.

## Table of Contents

- [Proposal App](#proposal-app)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Technologies](#technologies)
  - [Installation](#installation)
  - [Usage](#usage)
  - [Contributing](#contributing)

## Introduction

The Proposal App is a microservices application that allows users to create and manage proposals. It provides an API for creating new proposals, retrieving existing proposals, and sending notifications using RabbitMQ.

## Technologies

The following technologies are used in this project:

- Java
- Spring Boot
- RabbitMQ
- PostgreSQL
- Docker

## Installation

To run the Proposal App locally, follow these steps:

1. Clone the repository:

    ```shell
    git clone https://github.com/leonardomeirels55/proposal-app.git
    ```

2. Configure the application properties:

    Open the `application.properties` file located in `src/main/resources` and update the database connection details and RabbitMQ configuration according to your environment or env in `docker-compose`.

3. Run the application:

    ```shell
    docker compose up or docker-compose up
    ```

## Usage

Once the application is running, you can access the API endpoints using a tool like Postman or cURL. Here are some example requests:

- Create a new proposal:
    ```http
    POST /proposal
    Content-Type: application/json
    ```
        {
        "nome": "leonardo",
        "sobrenome": "meireles",
        "telefone": "55999999",
        "cpf": "111.111.111.11",
        "renda": 100,
        "valorSolicitado": 1000,
        "prazoPagamento": 24
        }
- Get all proposals:
    ```http
    GET /proposal
    ```

- Get a specific proposal:
    ```http
    GET /proposal/{id}
    ```

## Contributing

Contributions are welcome! If you have any ideas, suggestions, or bug reports, please open an issue or submit a pull request.
