package com.ms.user.domain;

import java.util.List;
import java.util.UUID;

import com.ms.user.domain.events.UserCreatedEvent;
import com.ms.user.shared.AggregateRoot;
import com.ms.user.shared.Guard;
import com.ms.user.shared.GuardArg;
import com.ms.user.shared.Result;

public class User extends AggregateRoot<UserProps> {
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

    var user = new User(props, id);

    boolean wasIdProvided = id != null;
    if (!wasIdProvided) {
      user.raiseEvent(new UserCreatedEvent(user));
    }

    return Result.ok(user);
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
