package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.command.UserPasswordDetails;
import com.hospital_app.user_service.application.port.in.user.ChangePasswordUserUseCase;
import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.security.PasswordEncoderService;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;

import java.util.UUID;

public class ChangePasswordUserUseCaseImpl implements ChangePasswordUserUseCase {

    private final CustomUserRepository customUserRepository;
    private final PasswordEncoderService passwordEncoder;
    private final InputValidator<UserPasswordDetails> userInputValidator;

    public ChangePasswordUserUseCaseImpl(CustomUserRepository customUserRepository, PasswordEncoderService passwordEncoder, InputValidator<UserPasswordDetails> userInputValidator) {
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userInputValidator = userInputValidator;
    }

    @Override
    public void execute(UserPasswordDetails userPasswordDetails) {
        userInputValidator.validate(userPasswordDetails);
        UUID userId = userPasswordDetails.userId();
        String newPassword = userPasswordDetails.newPassword();
        String passwordHash = passwordEncoder.encode(newPassword);
        customUserRepository.updateUserPassword(passwordHash, userId);
    }
}
