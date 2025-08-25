package com.hospital_app.user_service.application.port.in.user;

import java.util.UUID;

public interface ChangeStatusUserUseCase {
    void execute(UUID id, boolean status);
}
