package com.ms.user.controllers;

import com.ms.user.dtos.ErrorResponse;
import com.ms.user.dtos.SaveUserDto;
import com.ms.user.usecases.SaveUserUseCase;
import com.ms.user.usecases.errors.SaveUserErrors;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<?> saveUser(@RequestBody @Valid SaveUserDto userDto) {
    try {
      saveUserUseCase.execute(userDto);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      HttpStatusCode httpStatus;

      if (e instanceof SaveUserErrors.UserAlreadyExists) {
        httpStatus = HttpStatus.CONFLICT;
      } else {
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      }

      var errorResponse = new ErrorResponse(
          httpStatus.value(),
          e.getMessage(),
          "/users");

      return new ResponseEntity<>(errorResponse, httpStatus);
    }
  }
}
