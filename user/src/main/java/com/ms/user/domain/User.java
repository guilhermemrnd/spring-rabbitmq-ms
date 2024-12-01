package com.ms.user.domain;

import java.util.List;
import java.util.UUID;

import com.ms.user.shared.Entity;
import com.ms.user.shared.Guard;
import com.ms.user.shared.GuardArg;
import com.ms.user.shared.Result;

public class User extends Entity<UserProps> {
  private User(UserProps props, UUID id) {
    super(props, id);
  }

  private User(UserProps props) {
    super(props);
  }

  public static Result<User> create(UserProps props, UUID id) {
    var guardResult = Guard.againstNullBulk(List.of(
        new GuardArg(props.getName(), "name"),
        new GuardArg(props.getEmail(), "email")));

    if (guardResult.isFailure()) {
      return Result.fail(guardResult.getError());
    }

    if (id == null) {
      return Result.ok(new User(props));
    }

    return Result.ok(new User(props, id));
  }

  /* ---- Getters and Setters ---- */

  public String getName() {
    return props.getName();
  }

  public void setName(String name) {
    this.props.setName(name);
  }

  public String getEmail() {
    return props.getEmail();
  }

  public void setEmail(String email) {
    this.props.setEmail(email);
  }
}
