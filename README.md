# Mercedes KFZ

Welcome to Mercedes KFZ, a Spring Boot and angular project for managing car configurations.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)

## Prerequisites

Before running the application, ensure you have the following installed on your system:

- Docker Desktop for windows or Mac
- Docker and Docker Compose for Linux

## Getting Started

1. Clone the repository:

```bash
git clone https://github.com/patagan/mercedes_task.git
```

Navigate to the folder mercedes_task

```bash
cd mercedes_task
```

Run the docker-compose

```bash
docker-compose up -d
```

The docker compose changes the entrypoint of the backend to wait 10s for the database to be available.

## Usage

Open a web browser and navigate to http://localhost:8080/swagger-ui/index.html to test the backend endpoints.
Open a web browser and navigate to http://localhost:4200 to open the web app.

You can login with the initial user or register with a new user:

```
username: admin 
password: admin
```
