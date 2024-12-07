package com.ms.email.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Getter
@Service
public class ConfigService implements IConfigService {
  @Value("${spring.mail.username}")
  private String emailFrom;
}
