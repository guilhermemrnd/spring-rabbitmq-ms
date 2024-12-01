package com.ms.user.controllers;

import com.ms.user.dtos.SaveUserDto;
import com.ms.user.usecases.SaveUserUseCase;
import com.ms.user.usecases.errors.SaveUserErrors;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  final SaveUserUseCase saveUserUseCase;

  public UserController(SaveUserUseCase saveUserUseCase) {
    this.saveUserUseCase = saveUserUseCase;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public void saveUser(@RequestBody @Valid SaveUserDto userDto) {
    // try {
      saveUserUseCase.execute(userDto);
    // } catch (SaveUserErrors.UserAlreadyExists e) {
    //   return ResponseEntity.status(HttpStatus.CONFLICT).body(
    //       new ExceptionDto(
    //           HttpStatus.CONFLICT.value(),
    //           e.getMessage(),
    //           "/users"));
    // } catch (Exception e) {
    //   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
    //       new ExceptionDto(
    //           HttpStatus.INTERNAL_SERVER_ERROR.value(),
    //           e.getMessage(),
    //           "/users"));
    // }
  }
}
