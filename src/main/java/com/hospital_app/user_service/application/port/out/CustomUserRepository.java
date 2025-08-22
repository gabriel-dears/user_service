package com.hospital_app.user_service.application.port.out;

import com.hospital_app.user_service.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface CustomUserRepository {
    Optional<User> findById(UUID id);
}
