package com.ms.user.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.user.domain.User;
import com.ms.user.dtos.EmailDto;

@Component
public class UserProducer {
  final RabbitTemplate rabbitTemplate;

  public UserProducer(RabbitTemplate rabbitTempalte) {
    this.rabbitTemplate = rabbitTempalte;
  }

  @Value(value = "${broker.queue.email.name}")
  private String routingKey;

  public void publishMessageEmail(User user) {
    var emailDto = new EmailDto(
        user.getId(),
        user.getEmail(),
        "Cadastro realizado com sucesso",
        String.format(
            "%s, seja bem-vindo(a)! \nAgradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma.",
            user.getName()));

    try {
      rabbitTemplate.convertAndSend("", routingKey, emailDto);
    } catch (Exception e) {
      throw new RuntimeException(String.format(
          "Failed to publish message to email queue. %s", e.getMessage()));
    }
  }
}
