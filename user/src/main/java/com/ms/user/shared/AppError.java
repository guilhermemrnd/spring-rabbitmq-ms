package com.ms.user.shared;

public class AppError {
  public static class UnexpectedError extends RuntimeException {
    public UnexpectedError(String message) {
      super(message);
    }
  }
}
