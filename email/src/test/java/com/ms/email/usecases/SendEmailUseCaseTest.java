package com.ms.email.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ms.email.domain.StatusEmail;
import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.infra.IEmailRepository;
import com.ms.email.services.IConfigService;
import com.ms.email.services.IEmailService;
import com.ms.email.shared.AppError;

public class SendEmailUseCaseTest {
  private IEmailRepository emailRepo;
  private IEmailService emailService;
  private IConfigService configService;
  private SendEmailUseCase sendEmailUseCase;

  @BeforeEach
  void setUp() {
    this.emailRepo = Mockito.mock(IEmailRepository.class);
    this.emailService = Mockito.mock(IEmailService.class);
    this.configService = Mockito.mock(IConfigService.class);
    this.sendEmailUseCase = new SendEmailUseCase(emailRepo, emailService, configService);
  }

  @Test
  void shouldThrowUnexpectedErrorWhenEmailCreationFails() {
    EmailRecordDto input = new EmailRecordDto(null, null, null, null);

    assertThrows(AppError.UnexpectedError.class, () -> sendEmailUseCase.execute(input));
    verify(emailRepo, never()).save(any());
  }

  @Test
  void shouldSaveEmailAsErrorWhenSendingFails() {
    EmailRecordDto input = new EmailRecordDto(UUID.randomUUID(), "to@test.com", "Subject Test", "Text Test");

    when(configService.getEmailFrom()).thenReturn("from@test.com");
    doThrow(new RuntimeException("Error")).when(emailService).sendEmail(any());

    sendEmailUseCase.execute(input);

    verify(emailRepo).save(argThat(email -> email.getStatusEmail().equals(StatusEmail.ERROR)));
  }

  @Test
  void shouldSaveEmailAsSentWhenEverythingSucceeds() {
    EmailRecordDto input = new EmailRecordDto(UUID.randomUUID(), "to@test.com", "Subject Test", "Text Test");

    when(configService.getEmailFrom()).thenReturn("from@test.com");
    doNothing().when(emailService).sendEmail(any());

    sendEmailUseCase.execute(input);

    verify(emailRepo).save(argThat(email -> email.getStatusEmail().equals(StatusEmail.SENT)));
  }

}
