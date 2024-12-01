package com.ms.user.shared;

public class GuardArg {
  private final Object argValue;
  private final String argName;

  public GuardArg(Object argValue, String argName) {
    this.argValue = argValue;
    this.argName = argName;
  }

  public Object getArgValue() {
    return argValue;
  }

  public String getArgName() {
    return argName;
  }
}
