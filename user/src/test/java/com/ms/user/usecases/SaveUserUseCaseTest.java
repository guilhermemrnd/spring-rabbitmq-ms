package com.ms.user.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ms.user.dtos.SaveUserDto;
import com.ms.user.infra.IUserRepository;
import com.ms.user.shared.AppError;
import com.ms.user.usecases.errors.SaveUserErrors;

public class SaveUserUseCaseTest {
  private IUserRepository userRepo;
  private SaveUserUseCase saveUserUseCase;

  @BeforeEach
  void setUp() {
    this.userRepo = Mockito.mock(IUserRepository.class);
    this.saveUserUseCase = new SaveUserUseCase(userRepo);
  }

  @Test
  void shouldThrowUserAlreadyExistsWhenUserExists() {
    String email = "test@test.com";
    var props = new SaveUserDto("Test User", email);

    when(userRepo.exists(email)).thenReturn(true);

    assertThrows(SaveUserErrors.UserAlreadyExists.class,
        () -> saveUserUseCase.execute(props));
    verify(userRepo, never()).save(any());
  }

  @Test
  void shouldThrowUnexpectedErrorWhenUserCreationFails() {
    String email = "test@test.com";
    SaveUserDto props = new SaveUserDto("", email);

    when(userRepo.exists(email)).thenReturn(false);

    assertThrows(AppError.UnexpectedError.class,
        () -> saveUserUseCase.execute(props));
    verify(userRepo, never()).save(any());
  }

  @Test
  void shouldSaveUserWhenUserDoesNotExistAndCreationSucceeds() {
    String email = "test@test.com";
    SaveUserDto props = new SaveUserDto("Test User", email);

    when(userRepo.exists(email)).thenReturn(false);

    saveUserUseCase.execute(props);

    verify(userRepo, times(1)).save(any());
  }
}
