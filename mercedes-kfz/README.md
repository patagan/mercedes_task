# Mercedes KFZ

Welcome to Mercedes KFZ, a Spring Boot project for managing car configurations.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)

## Introduction

Mercedes KFZ is a backend application built with Spring Boot that allows users to configure and manage car specifications, such as class, type, motor, color, and extras. It provides a user-friendly interface for creating, updating, and deleting car configurations.

## Features

- User authentication
- User Login and registration
- CRUD operations for car configurations
- Integration with database for persistence
- Error handling and validation

## Prerequisites

Before running the application, ensure you have the following installed on your system:

- Java Development Kit (JDK) 17 or later
- Gradle
- MySQL database server

## Getting Started

You will need to have a running Mysql database to be able to start the application. 
Once you have that follow the next steps:

1. Build the project:

```bash
./gradlew build
```

2. Run the jar file:

```bash
 java -jar build/libs/mercedes-kfz-0.0.1-SNAPSHOT.jar
```

By starting the application, database schemas and test data will be created in the database.

You can login with the initial user:

```
username: admin 
password: admin
```

## Usage

1. Open a web browser and navigate to http://localhost:8080/swagger-ui/index.html.
2. Try out the Rest APIs
