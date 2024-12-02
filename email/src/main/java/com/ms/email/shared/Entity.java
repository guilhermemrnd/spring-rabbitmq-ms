package com.ms.email.shared;

import java.util.Objects;
import java.util.UUID;

public abstract class Entity<T> {
  private final UUID _id;
  protected final T props;

  public Entity(T props, UUID id) {
    this._id = Objects.requireNonNullElse(id, UUID.randomUUID());
    this.props = props;
  }

  public UUID getId() {
    return _id;
  }
}
