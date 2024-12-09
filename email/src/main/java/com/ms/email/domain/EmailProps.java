package com.ms.email.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailProps {
  private final UUID userId;
  private final String emailFrom;
  private final String emailTo;
  private final String subject;
  private final String text;
  private LocalDateTime sendDateEmail = LocalDateTime.now();
  private StatusEmail statusEmail;

  public void setStatusEmail(StatusEmail statusEmail) {
    this.statusEmail = statusEmail;
  }
}
