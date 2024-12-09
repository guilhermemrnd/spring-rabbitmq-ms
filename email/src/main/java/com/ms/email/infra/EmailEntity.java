package com.ms.email.infra;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.ms.email.domain.StatusEmail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private UUID id;
  private UUID userId;
  private String emailFrom;
  private String emailTo;
  private String subject;
  @Column(columnDefinition = "TEXT")
  private String text;
  private LocalDateTime sendDateEmail;
  private StatusEmail statusEmail;
}
