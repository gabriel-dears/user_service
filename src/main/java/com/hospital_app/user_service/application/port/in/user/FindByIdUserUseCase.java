package com.hospital_app.user_service.application.port.in.user;

import com.hospital_app.user_service.domain.model.User;

import java.util.UUID;

public interface FindByIdUserUseCase {
    User execute(UUID id);
}
