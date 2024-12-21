# Spring RabbitMQ Microservices

This project demonstrates a microservices architecture with two services: **User** and **Email**. These services communicate asynchronously using RabbitMQ, with PostgreSQL as the database.

## Technologies Used

- **Java** with **Spring Boot** for building microservices.
- **RabbitMQ** (hosted on CloudAMQP) for inter-service communication.
- **PostgreSQL** as the relational database.
- **Docker** for containerization and environment setup.
- **Testcontainers** for integration testing.

## Architecture

The application follows the principles of **Clean Architecture**, organizing code into distinct layers:

- **Domain Layer**: Contains the core business logic and domain entities, independent of external frameworks.
- **Application Layer**: Implements use cases, orchestrating interactions between the domain and infrastructure layers.
- **Infrastructure Layer**: Handles external concerns like database access and messaging.

By adhering to the **Dependency Inversion Principle**, the application depends on abstractions rather than concrete implementations, enhancing flexibility and testability.

Domain events are utilized to decouple the domain from subdomains. For instance, when a user is created, a domain event triggers the email microservice to send a confirmation email, following the observer pattern.

## Testing Strategy

- **Unit Tests**: Focus on the application logic within use cases to ensure correctness.
- **Integration Tests**: Validate interactions with external components, including repositories, email services, and RabbitMQ messaging, using Testcontainers to manage dependencies in a controlled environment.

## Setup Instructions

To run this project locally, follow these steps:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/guilhermemrnd/spring-rabbitmq-ms.git
   cd spring-rabbitmq-ms
   ```

2. **Install Prerequisites**:
   - Ensure **Docker** is installed to manage service containers.
   - Install **Java 21** or higher.
   - Install **Maven** for building the project.

3. **Start Dependent Services**:
   Use Docker Compose to set up PostgreSQL:
   ```bash
   docker-compose up -d
   ```

4. **Configure the Environment Files**:
   Each microservice uses a `.env` file to manage its configuration. Ensure you provide the required environment variables:

   **User Microservice `.env`**:
   ```env
   # Database
   DATABASE_URL=jdbc:postgresql://localhost:5432/ms_user_db
   DATABASE_USERNAME=your_username
   DATABASE_PASSWORD=your_password

   # RabbitMQ
   RABBITMQ_URL=amqp://your_cloudamqp_url
   ```

   **Email Microservice `.env`**:
   ```env
   # Database
   DATABASE_URL=jdbc:postgresql://localhost:5433/ms_email_db
   DATABASE_USERNAME=your_username
   DATABASE_PASSWORD=your_password

   # RabbitMQ
   RABBITMQ_URL=amqp://your_cloudamqp_url

   # Gmail
   EMAIL_USERNAME=your_email@gmail.com
   EMAIL_PASSWORD=your_email_password
   ```

5. **Set Up RabbitMQ on CloudAMQP**:
   - Visit [CloudAMQP](https://www.cloudamqp.com/) and create a free or paid account.
   - Create a new RabbitMQ instance.
   - Copy the provided **AMQP URL** and set it as the value for `RABBITMQ_URL` in the `.env` files.

6. **Build and Run the Application**:
   Execute the following commands:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

7. **Verify Setup**:
   - Access RabbitMQ management interface via CloudAMQP.
   - Ensure PostgreSQL databases are running and accessible.

By following these steps, you can explore the project's implementation and assess its alignment with best practices in microservices architecture, clean code principles, and effective testing strategies.
