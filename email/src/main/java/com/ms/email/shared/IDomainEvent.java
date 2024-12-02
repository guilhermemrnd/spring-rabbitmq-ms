package com.ms.email.shared;

import java.time.LocalDateTime;

public interface IDomainEvent {
  LocalDateTime dateTimeOccurred();
  String getAggregateId();
}
