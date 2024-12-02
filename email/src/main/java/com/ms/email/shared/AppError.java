package com.ms.email.shared;

public class AppError {
  public static class UnexpectedError extends RuntimeException {
    public UnexpectedError(String message) {
      super(message);
    }
  }
}
