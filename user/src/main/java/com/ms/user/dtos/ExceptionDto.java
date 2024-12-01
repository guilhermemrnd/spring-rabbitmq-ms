package com.ms.user.dtos;

import java.sql.Date;

import lombok.Getter;

@Getter
public class ExceptionDto {
  private final Date timestamp;
  private final int status;
  private final String message;
  private final String path;

  public ExceptionDto(int status, String message, String path) {
    this.timestamp = new Date(System.currentTimeMillis());
    this.status = status;
    this.message = message;
    this.path = path;
  }
}
