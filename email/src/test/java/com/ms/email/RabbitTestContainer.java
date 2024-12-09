package com.ms.email;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public interface RabbitTestContainer {
  static String DOCKER_IMAGE_NAME = "rabbitmq:3-management-alpine";

  @Container
  static RabbitMQContainer rabbitMQ = new RabbitMQContainer(DOCKER_IMAGE_NAME);

  @DynamicPropertySource
  static void rabbitMQProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.rabbitmq.addresses", rabbitMQ::getAmqpUrl);
  }
}
