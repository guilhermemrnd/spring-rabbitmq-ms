package com.ms.user.infra;

import java.io.Serializable;
import java.util.UUID;

import com.ms.user.shared.DomainEvents;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private UUID userId;
  private String name;
  private String email;

  @PostPersist
  private void afterSave() {
    DomainEvents.dispatchAggregateEvents(userId);
  }
}
