package com.ms.email.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.ms.email.domain.Email;
import com.ms.email.domain.EmailProps;

@SpringBootTest
@ActiveProfiles("test")
public class EmailServiceTest {
  private GreenMail greenMail;

  @Autowired
  private EmailService emailService;

  @BeforeEach
  void setUp() {
    greenMail = new GreenMail(ServerSetupTest.SMTP);
    greenMail.start();
  }

  @AfterEach
  void tearDown() {
    greenMail.stop();
  }

  @Test
  public void shouldSendEmail() {
    var emailProps = new EmailProps(
        UUID.randomUUID(),
        "from@test.com",
        "to@test.com",
        "Test Subject",
        "Test Text");

    var email = Email.create(emailProps, null).getValue();

    try {
      emailService.sendEmail(email);

      greenMail.waitForIncomingEmail(5000, 1);

      var receivedMessages = greenMail.getReceivedMessages();
      assertEquals(1, receivedMessages.length);

      var message = receivedMessages[0];
      assertEquals("Test Subject", message.getSubject());
      assertEquals("to@test.com", message.getAllRecipients()[0].toString());
      assertEquals("Test Text", message.getContent().toString());
    } catch (Exception e) {
      e.printStackTrace();
      fail(String.format("Exception was thrown. %s", e.getMessage()));
    }
  }
}
