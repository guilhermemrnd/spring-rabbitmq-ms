package com.ms.user.producers;

import static org.junit.jupiter.api.Assertions.*;
import static org.awaitility.Awaitility.*;

import java.time.Duration;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.ms.user.RabbitTestContainer;
import com.ms.user.domain.User;
import com.ms.user.domain.UserProps;
import com.ms.user.dtos.EmailDto;

@SpringBootTest
@ActiveProfiles("test")
public class UserProducerTest implements RabbitTestContainer {
  @Autowired
  private UserProducer userProducer;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Value(value = "${broker.queue.email.name}")
  private String queue;

  @Test
  public void shouldPublishEmailMessage() {
    var userProps = new UserProps("Test User", "test@test.com");
    var user = User.create(userProps, null).getValue();

    userProducer.publishMessageEmail(user);

    await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
      int queueMessageCount = Objects.requireNonNullElse(
          rabbitTemplate.execute(channel -> channel.queueDeclare(queue, true, false, false, null).getMessageCount()),
          0);

      var queuedMessage = (EmailDto) Objects.requireNonNull(
          rabbitTemplate.receiveAndConvert(queue));

      var isMessageQueued = queueMessageCount == 1
          && queuedMessage.getEmailTo().equals("test@test.com");

      assertTrue(isMessageQueued);
    });
  }

  @TestConfiguration
  public static class RabbitMQTestConfig {
    @Bean
    public Queue queue(@Value("${broker.queue.email.name}") String queue) {
      return new Queue(queue, true);
    }
  }
}
