package com.ms.user.domain.events;

import java.time.LocalDateTime;

import com.ms.user.domain.User;
import com.ms.user.shared.IDomainEvent;

public class UserCreatedEvent implements IDomainEvent {
  private final LocalDateTime dateTimeOccurred;
  private final User user;

  public UserCreatedEvent(User user) {
    dateTimeOccurred = LocalDateTime.now();
    this.user = user;
  }

  @Override
  public LocalDateTime dateTimeOccurred() {
    return dateTimeOccurred;
  }

  @Override
  public String getAggregateId() {
    return user.getId().toString();
  }

  public User getUser() {
    return user;
  }
}
