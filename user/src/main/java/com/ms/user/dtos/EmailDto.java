package com.ms.user.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailDto {
  private UUID userId;
  private String emailTo;
  private String subject;
  private String text;
}
