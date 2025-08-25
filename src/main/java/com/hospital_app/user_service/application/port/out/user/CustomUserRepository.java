package com.hospital_app.user_service.application.port.out.user;

import com.hospital_app.user_service.application.common.pagination.ApplicationPage;
import com.hospital_app.user_service.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface CustomUserRepository {
    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);

    User create(User user);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    ApplicationPage<User> findAll(int pageNumber, int pageSize);

    boolean existsByEmailForAnotherId(String email, UUID id);

    boolean existsByUsernameForAnotherId(String username, UUID id);

    User update(User user);
}
