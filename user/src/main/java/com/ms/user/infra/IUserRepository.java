package com.ms.user.infra;

import com.ms.user.domain.User;

public interface IUserRepository {
  boolean exists(String email);
  void save(User user);
}
