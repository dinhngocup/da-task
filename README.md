# Task Manager Project

## Overview

This project is designed to support CRUD operations for tasks, distinguishing between admin and employee roles. 

## Tech Stack
- Spring Boot 3.0
- Spring Security 6 with JWT
- Spring Data JPA
- MySQL
- Docker

## Roles
- **Admin:** Can CRUD tasks and CRUD users.
- **Employee:** Only update tasks.
- **All Users:** Only get all tasks.

## Installation

This project can be run in a container. Follow the guidelines below for setup:

1. **Java 17 Setup:**
   Install Java 17 on your system.

2. **Build and Run:**
   Execute the following commands to build and run the project:

   ```bash
   mvn clean install
   docker-compose up
   ```
## Test Application

### Admin role
If your database doesn't have any admin account, we provide an api to create superuser with admin
role. This api is authenticated by API key with Base64 encoded.

   ```bash
      curl --location 'localhost:8080/users/admin' \
      --header 'Content-Type: application/json' \
      --header 'Authorization: ZGluaG5nb2N1eWVucGh1b25n' \
      --data '{
          "username": "superadmin",
          "pswd": "superadmin",
          "roleId": "ROLE_ADMIN"
      }'
   ```
With admin account, you can create other accounts with different roles. We provide 2 roles:
- ROLE_AMDIN
- ROLE_EMPLOYEE

### Login
To obtain an access token for authentication, use the following curl command:

   ```bash
      curl --location 'localhost:8080/auth/login' \
      --header 'Content-Type: application/json' \
      --data '{
          "username": "superuser",
          "pswd": "superuser"
      }'
   ````

**Note**: All the APIs of admin and employee role need contain access_token from Login API.

### Employee role
**Update current task**
   ```bash
      curl --location --request PUT 'localhost:8080/tasks/1' \
      --header 'Content-Type: application/json' \
      --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXBlcmFkbWluMiIsImlhdCI6MTcwMDAzOTY1NSwiZXhwIjoxNzAwOTAzNjU1fQ.1ds86DA-wOG2sKRHROPYUzqYHnFHDmiEHNlS3RY1gYk' \
      --data '{
          "title": "Phuong Dinh",
          "description": "Home Assignment for the first round",
          "completed": false
      }'
   ````

### Anonymous User
**Get all Tasks**: To retrieve all tasks anonymously, use the following curl command:

   ```bash
      curl --location 'localhost:8080/tasks'
   ```

### Other APIs
For more API endpoints, please download the Postman collection from this [link](https://drive.google.com/drive/folders/1JptngX8zztQD4CCTpZqwaejGpEubFXy9?usp=sharing).



