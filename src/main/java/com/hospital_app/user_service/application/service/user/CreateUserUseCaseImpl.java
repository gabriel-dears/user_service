package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.port.in.user.CreateUserUseCase;
import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.security.PasswordEncoderService;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.User;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final CustomUserRepository customUserRepository;
    private final PasswordEncoderService passwordEncoder;
    private final InputValidator<User> userRequestInputValidator;

    public CreateUserUseCaseImpl(CustomUserRepository customUserRepository, PasswordEncoderService passwordEncoder, InputValidator<User> userRequestInputValidator) {
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRequestInputValidator = userRequestInputValidator;
    }

    @Override
    public User execute(User user) {
        userRequestInputValidator.validate(user);
        user.setEnabled(true);
        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);


        return customUserRepository.create(user);
    }
}
