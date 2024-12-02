package com.ms.email.infra;

import com.ms.email.domain.Email;

public interface IEmailRepository {
  void save(Email email);
}
