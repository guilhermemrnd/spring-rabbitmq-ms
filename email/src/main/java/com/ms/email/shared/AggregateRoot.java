package com.ms.email.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AggregateRoot<T> extends Entity<T> {
  private List<IDomainEvent> domainEvents = new ArrayList<>();

  public AggregateRoot(T props, UUID id) {
    super(props, id);
  }

  protected void raiseEvent(IDomainEvent event) {
    domainEvents.add(event);
    DomainEvents.markAggregateForDispatch(this);
  }

  public void clearEvents() {
    domainEvents.clear();
  }

  public List<IDomainEvent> getDomainEvents() {
    return domainEvents;
  }
}
