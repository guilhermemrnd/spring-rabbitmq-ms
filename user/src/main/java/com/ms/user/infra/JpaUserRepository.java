package com.ms.user.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
  boolean existsByEmail(String email);
}
