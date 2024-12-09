package com.ms.email.infra;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ms.email.PostgresTestContainer;
import com.ms.email.domain.Email;
import com.ms.email.domain.EmailProps;

@SpringBootTest
public class EmailRepositoryTest implements PostgresTestContainer {
  @Autowired
  private EmailRepository emailRepo;

  @Autowired
  private JpaEmailRepository jpaEmailRepo;

  @Test
  void shouldSaveEmailAsSent() {
    var email = mockEmail();
    email.markAsSent();

    emailRepo.save(email);

    var rawEmailOpt = jpaEmailRepo.findById(email.getId());
    assertTrue(rawEmailOpt.isPresent());
    assertEmail(email, rawEmailOpt.get());
  }

  @Test
  void shouldSaveEmailAsError() {
    var email = mockEmail();
    email.markAsError();

    emailRepo.save(email);

    var rawEmailOpt = jpaEmailRepo.findById(email.getId());
    assertTrue(rawEmailOpt.isPresent());
    assertEmail(email, rawEmailOpt.get());
  }

  private Email mockEmail() {
    var emailProps = new EmailProps(
        UUID.randomUUID(),
        "from@test.com",
        "to@test.com",
        "Test Subject",
        "Test Text");

    return Email.create(emailProps, null).getValue();
  }

  private void assertEmail(Email email, EmailEntity rawEmail) {
    assertEquals(email.getEmailFrom(), rawEmail.getEmailFrom());
    assertEquals(email.getEmailTo(), rawEmail.getEmailTo());
    assertEquals(email.getSubject(), rawEmail.getSubject());
    assertEquals(email.getText(), rawEmail.getText());
    assertEquals(email.getStatusEmail(), rawEmail.getStatusEmail());
  }
}
