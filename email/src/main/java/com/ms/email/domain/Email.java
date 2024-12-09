package com.ms.email.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ms.email.shared.Entity;
import com.ms.email.shared.Result;
import com.ms.email.shared.GuardArg;
import com.ms.email.shared.Guard;

public class Email extends Entity<EmailProps> {
  private Email(EmailProps props, UUID id) {
    super(props, id);
  }

  public static Result<Email> create(EmailProps props, UUID id) {
    var guardResult = Guard.againstNullBulk(List.of(
        new GuardArg(props.getUserId(), "userId"),
        new GuardArg(props.getEmailFrom(), "emailFrom"),
        new GuardArg(props.getEmailTo(), "emailTo"),
        new GuardArg(props.getSubject(), "subject"),
        new GuardArg(props.getText(), "text")));

    if (guardResult.isFailure()) {
      return Result.fail(guardResult.getError());
    }

    var email = new Email(props, id);

    return Result.ok(email);
  }

  public void markAsSent() {
    props.setStatusEmail(StatusEmail.SENT);
  }

  public void markAsError() {
    props.setStatusEmail(StatusEmail.ERROR);
  }

  /* ---- Getters and Setters ---- */

  public UUID getUserId() {
    return props.getUserId();
  }

  public String getEmailFrom() {
    return props.getEmailFrom();
  }

  public String getEmailTo() {
    return props.getEmailTo();
  }

  public String getSubject() {
    return props.getSubject();
  }

  public String getText() {
    return props.getText();
  }

  public LocalDateTime getSendDateEmail() {
    return props.getSendDateEmail();
  }

  public StatusEmail getStatusEmail() {
    return props.getStatusEmail();
  }
}
