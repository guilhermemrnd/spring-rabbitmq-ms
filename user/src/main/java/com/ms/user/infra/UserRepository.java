package com.ms.user.infra;

import org.springframework.stereotype.Service;

import com.ms.user.domain.User;

import jakarta.transaction.Transactional;

@Service
public class UserRepository implements IUserRepository {
  private final JpaUserRepository jpaUserRepo;

  public UserRepository(JpaUserRepository jpaUserRepo) {
    this.jpaUserRepo = jpaUserRepo;
  }

  @Override
  public boolean exists(String email) {
    return jpaUserRepo.existsByEmail(email);
  }

  @Transactional
  @Override
  public void save(User user) {
    var rawUser = UserMapper.toPersistence(user);
    
    try {
      jpaUserRepo.save(rawUser);
    } catch (Exception err) {
      throw new RuntimeException("Failed to save user. " + err.getMessage());
    }
  }
}
