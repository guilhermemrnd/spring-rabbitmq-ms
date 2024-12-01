package com.ms.user.config;

import com.ms.user.infra.IUserRepository;
import com.ms.user.infra.JpaUserRepository;
import com.ms.user.infra.UserRepository;
import com.ms.user.usecases.SaveUserUseCase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  private final JpaUserRepository jpaUserRepository;

  public AppConfig(JpaUserRepository jpaUserRepository) {
    this.jpaUserRepository = jpaUserRepository;
  }

  @Bean
  public IUserRepository userRepository() {
    return new UserRepository(jpaUserRepository);
  }

  @Bean
  public SaveUserUseCase saveUserUseCase() {
    return new SaveUserUseCase(userRepository());
  }
}
