package com.ms.user.infra;

import com.ms.user.domain.User;
import com.ms.user.domain.UserProps;

public class UserMapper {
  public static User toDomain(UserEntity userRaw) {
    var props = new UserProps(
        userRaw.getName(),
        userRaw.getEmail());

    var userOrError = User.create(props, userRaw.getUserId());

    if (userOrError.isFailure()) {
      throw new RuntimeException(userOrError.getError());
    }

    return userOrError.getValue();
  }

  public static UserEntity toPersistence(User user) {
    return new UserEntity(
        user.getId(),
        user.getName(),
        user.getEmail());
  }
}
