package com.ms.user.configs;

import com.ms.user.email.AfterUserCreated;
import com.ms.user.infra.IUserRepository;
import com.ms.user.infra.JpaUserRepository;
import com.ms.user.infra.UserRepository;
import com.ms.user.producers.UserProducer;
import com.ms.user.usecases.SaveUserUseCase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  private final JpaUserRepository jpaUserRepository;
  private final UserProducer userProducer;

  public AppConfig(JpaUserRepository jpaUserRepository, UserProducer userProducer) {
    this.jpaUserRepository = jpaUserRepository;
    this.userProducer = userProducer;
  }

  @Bean
  public IUserRepository userRepository() {
    return new UserRepository(jpaUserRepository);
  }

  @Bean
  public SaveUserUseCase saveUserUseCase() {
    return new SaveUserUseCase(userRepository());
  }

  @Bean
  public AfterUserCreated afterUserCreated() {
    return new AfterUserCreated(userProducer);
  }
}
