package com.ms.email.services;

import com.ms.email.domain.Email;

public interface IEmailService {
  void sendEmail(Email emailData);
}
