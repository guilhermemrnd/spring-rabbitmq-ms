package com.ms.user.usecases.errors;

public class SaveUserErrors {
  public static class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String email) {
      super(String.format("User with email %s already exists", email));
    }
  }
}