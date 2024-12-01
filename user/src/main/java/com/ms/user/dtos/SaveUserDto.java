package com.ms.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SaveUserDto(@NotBlank String name, @NotBlank @Email String email) {
}
