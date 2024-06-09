# Spring Boot User Registration and Login System

Welcome to the Convomate project, a user registration and login system built using Spring Boot. This system is designed to provide a secure and efficient way for users to register, log in, and manage their accounts.
This project is a user registration and login system built using Spring Boot, Thymeleaf, and MySQL.



## Introduction
The Convomate project is developed as a college project, focusing on backend development using Spring Boot. It utilizes various Spring Boot features to implement user registration, login, and role-based access control functionalities. Additionally, it integrates with MySQL database for data storage.

The Spring Boot User Registration and Login System provides a simple and secure way for users to register an account and login to the system. It utilizes Spring Boot for backend development, Thymeleaf for server-side rendering, and MySQL for database management.
## Requirements

- Java Development Kit (JDK) 11 or higher
- Apache Maven
- MySQL Database Server
  
## Features

- User registration: Allows users to create an account by providing their first name, last name, email, and password.
- User login: Enables users to log in to the system using their email and password.
- Role-based access control: Implements role-based access control to restrict access to certain pages. For example, the `users.html` page is only accessible to users with the admin role.
- Database integration: Utilizes MySQL database for storing user information and roles.

## Installation

To run the project locally, follow these steps:

1. Clone the repository:

```bash
git clone https://github.com/Shevadesuyash/CONVOMATE-spring-boot-user-registration-and-Login.git
```

# Convomate - MySQL Database Setup and Spring Boot Application

This guide provides instructions on setting up the MySQL database and running the Convomate Spring Boot application.

## MySQL Database Setup

1. **Create Database**: Create a MySQL database named `Convomate`.

2. **Automatic Table Creation**: Spring Boot will automatically create the necessary tables based on the entity classes defined in the application. You don't need to execute SQL scripts manually for table creation.

## Update Application Properties

Update the `application.properties` file located in `src/main/resources` with your MySQL database connection details:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Convomate
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>

```

Replace <your_username> and <your_password> with your MySQL username and password.

## Build and Run

To build and run the project, execute the following commands:

```bash
./mvnw spring-boot:run
```
Access the application by navigating to ``` http://localhost:9090 ``` in your web browser.

## Usage
Once the application is running, you can:

1. Register a new user by navigating to the registration page (/register).
2. Log in to the system using your registered email and password.
3. Access the users.html page if you have the admin role assigned.
4. View the list of registered users on the users.html page.
