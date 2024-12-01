package com.ms.user.shared;

import java.util.UUID;

public abstract class Entity<T> {
  private final UUID _id;
  protected final T props;

  public Entity(T props, UUID id) {
    this._id = id;
    this.props = props;
  }

  public Entity(T props) {
    this(props, UUID.randomUUID());
  }

  public UUID getId() {
    return _id;
  }
}
