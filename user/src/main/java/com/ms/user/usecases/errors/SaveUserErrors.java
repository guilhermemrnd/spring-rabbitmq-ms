package com.ms.user.usecases.errors;

public class SaveUserErrors {
  public static class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists() {
      super(String.format("User already exists"));
    }
  }
}