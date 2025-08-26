package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.port.in.user.ExistsByIdUserUseCase;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.Role;

import java.util.UUID;

public class ExistsByIdUserUseCaseImpl implements ExistsByIdUserUseCase {

    private final CustomUserRepository customUserRepository;

    public ExistsByIdUserUseCaseImpl(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public boolean execute(UUID id, Role role) {
        return customUserRepository.existsByIdAndRole(id, role);
    }
}
