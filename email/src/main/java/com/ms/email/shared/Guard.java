package com.ms.email.shared;

import java.util.List;

public class Guard {
  public static Result<String> againstNull(Object argValue, String argumentName) {
    if (argValue == null) {
      return Result.fail(argumentName + " should not be null");
    }
    return Result.ok();
  }

  public static Result<String> againstNullBulk(List<GuardArg> args) {
    for (GuardArg arg : args) {
      var result = againstNull(arg.getArgValue(), arg.getArgName());
      if (!result.isSuccess()) {
        return result;
      }
    }
    return Result.ok();
  }
}
