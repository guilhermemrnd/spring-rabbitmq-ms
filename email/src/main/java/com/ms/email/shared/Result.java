package com.ms.email.shared;

public class Result<T> {
  private final boolean isSuccess;
  private final boolean isFailure;
  private final T value;
  private final String error;

  private Result(boolean isSuccess, String error, T value) {
    if (isSuccess && error != null) {
      throw new IllegalArgumentException("InvalidOperation: A result cannot be successful and contain an error");
    }
    if (!isSuccess && error == null) {
      throw new IllegalArgumentException("InvalidOperation: A failing result needs to contain an error message");
    }

    this.isSuccess = isSuccess;
    this.isFailure = !isSuccess;
    this.error = error;
    this.value = value;
  }

  public boolean isSuccess() {
    return isSuccess;
  }

  public boolean isFailure() {
    return isFailure;
  }

  public static <U> Result<U> ok(U value) {
    return new Result<>(true, null, value);
  }

  public static <U> Result<U> ok() {
    return new Result<>(true, null, null);
  }

  public static <U> Result<U> fail(String error) {
    return new Result<>(false, error, null);
  }

  public T getValue() {
    if (!isSuccess) {
      throw new IllegalStateException("Cannot access value of a failed result");
    }
    return value;
  }

  public String getError() {
    return error;
  }
}
