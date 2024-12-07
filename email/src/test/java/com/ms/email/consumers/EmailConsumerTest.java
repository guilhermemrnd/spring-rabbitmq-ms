package com.ms.email.consumers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.awaitility.Awaitility.*;

import java.time.Duration;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ms.email.RabbitTestContainer;
import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.usecases.SendEmailUseCase;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmailConsumerTest implements RabbitTestContainer {
  @Value(value = "${broker.queue.email.name}")
  private String queue;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @MockBean
  private SendEmailUseCase sendEmailUseCase;

  @Test
  public void shouldConsumeEmailQueue() {
    var emailDto = new EmailRecordDto(UUID.randomUUID(), "to@test.com", "Test Subject", "Test Text");

    doNothing().when(sendEmailUseCase).execute(any(EmailRecordDto.class));

    rabbitTemplate.convertAndSend(queue, emailDto);

    await().atMost(Duration.ofSeconds(5)).untilAsserted(() -> {
      verify(sendEmailUseCase, times(1)).execute(emailDto);
    });
  }
}
