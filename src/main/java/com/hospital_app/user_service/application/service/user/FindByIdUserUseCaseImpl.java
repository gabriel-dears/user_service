package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.port.in.user.FindByIdUserUseCase;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.UserNotFoundException;
import com.hospital_app.user_service.domain.model.User;

import java.util.UUID;

public class FindByIdUserUseCaseImpl implements FindByIdUserUseCase {

    private final CustomUserRepository customUserRepository;

    public FindByIdUserUseCaseImpl(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public User execute(UUID id) {
        return customUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }
}
