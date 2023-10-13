# To-Do List API with User Authentication

This is a simple To-Do List API built using Java, Spring Boot, and Maven. It also includes a Dockerfile for easy deployment. The API allows users to manage their tasks, providing endpoints for task creation, retrieval, and updating. Additionally, it offers user registration and uses Basic Authentication to secure the task-related routes.

## Technologies Used

- Java
- Spring Boot
- Maven
- Docker (Dockerfile included)

## Getting Started

To run this API on your local machine, follow these simple steps:

### Prerequisites

1. Install Java: Make sure you have Java installed on your system. You can download it from [here](https://www.java.com/en/download/).

2. Install Maven: You can download and install Maven by following the instructions [here](https://maven.apache.org/download.cgi).

3. Install Docker: If you wish to use Docker for deployment, you can download it from [Docker's official website](https://www.docker.com/products/docker-desktop).

### Installation

1. Clone this repository to your local machine:

   ```shell
   git clone https://github.com/wellrcosta/ToDoList.git
   ```

2. Navigate to the project directory:

   ```shell
   cd ToDoList
   ```

3. Build the project using Maven:

   ```shell
   mvn clean install
   ```

### Running the API

#### Using Docker

1. Build a Docker image from the provided Dockerfile:

   ```shell
   docker build -t todo-list-api .
   ```

2. Run the Docker container:

   ```shell
   docker run -p 8080:8080 todo-list-api
   ```

#### Without Docker

1. Run the application using Maven:

   ```shell
   mvn spring-boot:run
   ```

The API should now be up and running, and you can access it at `http://localhost:8080`.

## Usage

### Creating a User

To create a new user, make a POST request to the `/users/` endpoint with the following request body:

```json
{
	"username" : "reis",
	"name": "Wellington",
	"password": "123"
}
```

### Managing Tasks

- **Create a Task:** Send a POST request to `/tasks/` with the task details. Use the following request body as an example:

```json
{
	"title" : "New task",
	"description": "This is only a test",
	"startAt": "2023-10-14T15:00:00",
	"finishAt": "2023-10-14T18:00:00",
	"priority": "High"
}
```

- **Retrieve Tasks:** Send a GET request to `/tasks/` to get a list of all tasks.

- **Update a Task:** Send a PUT request to `/tasks/{taskId}` with the updated task details. Use the same request body as for creating a task or only a specific field.

### Authentication

To access the task management routes at `/tasks/`, you must use Basic Authentication with valid user credentials.

Feel free to explore the API and customize it as needed for your project!

Enjoy using your To-Do List API!