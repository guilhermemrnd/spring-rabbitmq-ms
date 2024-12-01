package com.ms.user.shared;

import java.time.LocalDateTime;

public interface IDomainEvent {
  LocalDateTime dateTimeOccurred();
  String getAggregateId();
}
