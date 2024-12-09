package com.ms.email.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ms.email.infra.EmailRepository;
import com.ms.email.services.ConfigService;
import com.ms.email.services.EmailService;
import com.ms.email.usecases.SendEmailUseCase;

@Configuration
public class AppConfig {
  @Autowired
  private EmailRepository emailRepository;
  @Autowired
  private EmailService emailService;
  @Autowired
  private ConfigService configService;

  @Bean
  SendEmailUseCase sendEmailUseCase() {
    return new SendEmailUseCase(emailRepository, emailService, configService);
  }
}
