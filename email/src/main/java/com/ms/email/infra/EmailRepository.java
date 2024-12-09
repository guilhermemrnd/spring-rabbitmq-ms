package com.ms.email.infra;

import org.springframework.stereotype.Service;

import com.ms.email.domain.Email;

import jakarta.transaction.Transactional;

@Service
public class EmailRepository implements IEmailRepository {
  private final JpaEmailRepository jpaEmailRepository;

  public EmailRepository(JpaEmailRepository jpaEmailRepository) {
    this.jpaEmailRepository = jpaEmailRepository;
  }

  @Override
  @Transactional
  public void save(Email email) {
    var rawEmail = EmailMapper.toPersistence(email);

    try {
      jpaEmailRepository.save(rawEmail);
    } catch (Exception e) {
      throw new RuntimeException("Failed to save email. " + e.getMessage());
    }
  }
}
