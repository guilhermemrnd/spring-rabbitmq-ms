package com.ms.user.infra;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ms.user.PostgresTestContainer;
import com.ms.user.domain.User;
import com.ms.user.domain.UserProps;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryTest implements PostgresTestContainer {
  @Autowired
  private UserRepository userRepo;

  @Autowired
  private JpaUserRepository jpaUserRepo;

  @Test
  public void shouldReturnTrueIfUserExists() {
    userRepo.save(mockUser("Test User", "test@test.com"));
    assertTrue(userRepo.exists("test@test.com"));
  }

  @Test
  public void shouldReturnFalseIfUserDoesNotExist() {
    assertFalse(userRepo.exists("test@test.com"));
  }

  @Test
  public void shouldSaveUser() {
    var user = mockUser("Test User", "test@test.com");

    userRepo.save(user);

    var rawUserOpt = jpaUserRepo.findById(user.getId());
    assertTrue(rawUserOpt.isPresent());
  }

  private User mockUser(String name, String email) {
    var userProps = new UserProps(name, email);
    return User.create(userProps, null).getValue();
  }
}
