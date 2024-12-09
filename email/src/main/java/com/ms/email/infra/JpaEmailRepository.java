package com.ms.email.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmailRepository extends JpaRepository<EmailEntity, UUID> {
}
