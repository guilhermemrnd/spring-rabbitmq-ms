package com.ms.email.usecases;

import org.springframework.beans.factory.annotation.Value;

import com.ms.email.domain.Email;
import com.ms.email.domain.EmailProps;
import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.infra.IEmailRepository;
import com.ms.email.services.IEmailService;
import com.ms.email.shared.AppError;

public class SendEmailUseCase {
  private final IEmailRepository emailRepository;
  private final IEmailService emailService;

  @Value("${spring.mail.username}")
  private String emailFrom;

  public SendEmailUseCase(IEmailRepository emailRepository, IEmailService emailService) {
    this.emailRepository = emailRepository;
    this.emailService = emailService;
  }

  public void execute(EmailRecordDto input) {
    var props = new EmailProps(
        input.userId(),
        emailFrom,
        input.emailTo(),
        input.subject(),
        input.text());

    var emailOrError = Email.create(props, null);
    
    if (emailOrError.isFailure()) {
      throw new AppError.UnexpectedError(emailOrError.getError());
    }

    var email = emailOrError.getValue();

    try {
      emailService.sendEmail(email);
      email.markAsSent();
    } catch (Exception e) {
      System.err.println(e.getMessage());
      email.markAsError();
    } finally {
      emailRepository.save(email);
    }
  }
}
