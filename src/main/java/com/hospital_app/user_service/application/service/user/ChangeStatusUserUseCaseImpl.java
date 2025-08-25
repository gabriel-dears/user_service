package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.port.in.user.ChangeStatusUserUseCase;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.UserNotFoundException;

import java.util.UUID;

public class ChangeStatusUserUseCaseImpl implements ChangeStatusUserUseCase {

    private final CustomUserRepository customUserRepository;

    public ChangeStatusUserUseCaseImpl(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public void execute(UUID id, boolean enabled) {
        if (!customUserRepository.existsById(id)) {
            throw new UserNotFoundException(String.format("User with id %s not found", id));
        }
        customUserRepository.changeUserStatus(id, enabled);
    }
}
