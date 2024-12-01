package com.ms.user.usecases;

import com.ms.user.shared.AppError;

import com.ms.user.domain.User;
import com.ms.user.domain.UserProps;
import com.ms.user.infra.IUserRepository;
import com.ms.user.dtos.SaveUserDto;
import com.ms.user.usecases.errors.SaveUserErrors;

public class SaveUserUseCase {
  private final IUserRepository userRepo;

  public SaveUserUseCase(IUserRepository userRepo) {
    this.userRepo = userRepo;
  }

  public void execute(SaveUserDto props) {
    boolean doesUserExist = userRepo.exists(props.email());
    if (doesUserExist) {
      throw new SaveUserErrors.UserAlreadyExists(props.email());
    }

    var userOrError = User.create(new UserProps(
        props.name(),
        props.email()), null);

    if (userOrError.isFailure()) {
      throw new AppError.UnexpectedError(userOrError.getError());
    }

    userRepo.save(userOrError.getValue());
  }
}
