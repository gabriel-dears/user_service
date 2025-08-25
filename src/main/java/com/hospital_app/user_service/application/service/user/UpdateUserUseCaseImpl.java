package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.mapper.UserMapper;
import com.hospital_app.user_service.application.port.in.user.UpdateUserUseCase;
import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.UserNotFoundException;
import com.hospital_app.user_service.domain.model.User;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final CustomUserRepository customUserRepository;
    private final InputValidator<User> inputValidator;

    public UpdateUserUseCaseImpl(CustomUserRepository customUserRepository, InputValidator<User> inputValidator) {
        this.customUserRepository = customUserRepository;
        this.inputValidator = inputValidator;
    }

    @Override
    public User execute(User user) {
        inputValidator.validate(user);
        User existingUser = customUserRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", user.getId())));
        return customUserRepository.update(UserMapper.fromInputToExistingUserForUpdate(user, existingUser));
    }
}
