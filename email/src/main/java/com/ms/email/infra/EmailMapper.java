package com.ms.email.infra;

import com.ms.email.domain.Email;
import com.ms.email.domain.EmailProps;

public class EmailMapper {
  public static Email toDomain(EmailEntity emailRaw) {
    var props = new EmailProps(
        emailRaw.getUserId(),
        emailRaw.getEmailFrom(),
        emailRaw.getEmailTo(),
        emailRaw.getSubject(),
        emailRaw.getText(),
        emailRaw.getSendDateEmail(),
        emailRaw.getStatusEmail());

    var emailOrError = Email.create(props, emailRaw.getId());

    if (emailOrError.isFailure()) {
      throw new RuntimeException(emailOrError.getError());
    }

    return emailOrError.getValue();
  }

  public static EmailEntity toPersistence(Email email) {
    return new EmailEntity(
        email.getId(),
        email.getUserId(),
        email.getEmailFrom(),
        email.getEmailTo(),
        email.getSubject(),
        email.getText(),
        email.getSendDateEmail(),
        email.getStatusEmail());
  }
}
