package com.ms.email.services;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ms.email.domain.Email;

@Service
public class EmailService implements IEmailService {
  public final JavaMailSender emailSender;

  public EmailService(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Override
  public void sendEmail(Email emailData) {
    var message = new SimpleMailMessage();
    message.setTo(emailData.getEmailTo());
    message.setSubject(emailData.getSubject());
    message.setText(emailData.getText());

    try {
      emailSender.send(message);
    } catch (MailException e) {
      throw new RuntimeException("Failed to send email. " + e.getMessage());
    }
  }
}
