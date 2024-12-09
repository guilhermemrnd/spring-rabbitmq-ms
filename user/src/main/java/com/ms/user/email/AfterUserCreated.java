package com.ms.user.email;

import com.ms.user.domain.events.UserCreatedEvent;
import com.ms.user.producers.UserProducer;
import com.ms.user.shared.DomainEvents;

public class AfterUserCreated {
  private final UserProducer userProducer;

  public AfterUserCreated(UserProducer userProducer) {
    this.userProducer = userProducer;
    setupSubscriptions();
  }

  public void setupSubscriptions() {
    DomainEvents.register(UserCreatedEvent.class, this::onUserCreated);
  }

  public void onUserCreated(UserCreatedEvent event) {
    userProducer.publishMessageEmail(event.getUser());
  }
}
