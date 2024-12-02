package com.ms.email.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.ms.email.infra.EmailRepository;
import com.ms.email.infra.IEmailRepository;
import com.ms.email.infra.JpaEmailRepository;
import com.ms.email.services.EmailService;
import com.ms.email.services.IEmailService;
import com.ms.email.usecases.SendEmailUseCase;

@Configuration
public class AppConfig {
  private final JpaEmailRepository jpaEmailRepository;
  private final JavaMailSender emailSender;

  public AppConfig(JpaEmailRepository jpaEmailRepository, JavaMailSender emailSender) {
    this.jpaEmailRepository = jpaEmailRepository;
    this.emailSender = emailSender;
  }

  @Bean
  public IEmailRepository emailRepository() {
    return new EmailRepository(jpaEmailRepository);
  }

  @Bean
  public IEmailService emailService() {
    return new EmailService(emailSender);
  }

  @Bean
  public SendEmailUseCase sendEmailUseCase() {
    return new SendEmailUseCase(emailRepository(), emailService());
  }
}
