package com.hospital_app.user_service.application.port.in.user;

import com.hospital_app.user_service.domain.model.User;

public interface CreateUserUseCase {
    User execute(User user);
}
