# Proposal App

This is a microservices application for managing proposals.

## Table of Contents

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

## Installation

To run the Proposal App locally, follow these steps:

1. Clone the repository:

    ```shell
    git clone https://github.com/your-username/proposal-app.git
    ```

2. Install the required dependencies:

    ```shell
    cd proposal-app
    mvn install
    ```

3. Configure the application properties:

    Open the `application.properties` file located in `src/main/resources` and update the database connection details and RabbitMQ configuration according to your environment.

4. Run the application:

    ```shell
    mvn spring-boot:run
    ```

## Usage

Once the application is running, you can access the API endpoints using a tool like Postman or cURL. Here are some example requests:

- Create a new proposal:
    ```http
    POST /proposals
    Content-Type: application/json

    {
        "title": "Sample Proposal",
        "description": "This is a sample proposal"
    }
    ```

- Get all proposals:
    ```http
    GET /proposals
    ```

- Get a specific proposal:
    ```http
    GET /proposals/{proposalId}
    ```

- Update a proposal:
    ```http
    PUT /proposals/{proposalId}
    Content-Type: application/json

    {
        "title": "Updated Proposal",
        "description": "This is an updated proposal"
    }
    ```

- Delete a proposal:
    ```http
    DELETE /proposals/{proposalId}
    ```

## Contributing

Contributions are welcome! If you have any ideas, suggestions, or bug reports, please open an issue or submit a pull request.