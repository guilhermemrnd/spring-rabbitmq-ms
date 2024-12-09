package com.ms.email.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.usecases.SendEmailUseCase;

@Component
public class EmailConsumer {
  private final SendEmailUseCase sendEmailUseCase;

  public EmailConsumer(SendEmailUseCase sendEmailUseCase) {
    this.sendEmailUseCase = sendEmailUseCase;
  }
  
  @RabbitListener(queues = "${broker.queue.email.name}")
  public void listenEmailQueue(@Payload EmailRecordDto dto) {
    sendEmailUseCase.execute(dto);
  }
}
