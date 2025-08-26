package com.hospital_app.user_service.application.port.in.user;

import com.hospital_app.user_service.domain.model.Role;

import java.util.UUID;

public interface ExistsByIdUserUseCase {
    boolean execute(UUID id, Role role);
}
