package com.ms.user.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.user.PostgresTestContainer;
import com.ms.user.dtos.SaveUserDto;
import com.ms.user.infra.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest implements PostgresTestContainer {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepo;

  @Test
  public void shouldSaveUserSuccessfully() throws Exception {
    var dto = new SaveUserDto("User Test", "test@test.com");

    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(dto)))
        .andExpect(status().isCreated());

    assertTrue(userRepo.exists(dto.email()));
  }

  @Test
  public void shouldThrowConflictWhenUserAlreadyExists() throws Exception {
    var dto = new SaveUserDto("User Test", "test2@test.com");

    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(dto)))
        .andExpect(status().isCreated());

    mockMvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(dto)))
        .andExpect(status().isConflict());
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
