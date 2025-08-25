package com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user;

import com.hospital_app.user_service.application.command.UserPasswordDetails;
import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.security.PasswordEncoderService;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.InvalidPasswordException;
import com.hospital_app.user_service.domain.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CorrectPasswordValidator implements InputValidator<UserPasswordDetails> {

    private final PasswordEncoderService passwordEncoder;
    private final CustomUserRepository customUserRepository;

    public CorrectPasswordValidator(PasswordEncoderService passwordEncoder, CustomUserRepository customUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customUserRepository = customUserRepository;
    }

    @Override
    public void validate(UserPasswordDetails userPasswordDetails) {
        UUID userId = userPasswordDetails.userId();
        var existingUser = customUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", userId)));
        String passwordHash = existingUser.getPasswordHash();
        String newPassword = userPasswordDetails.newPassword();
        if (!passwordEncoder.matches(newPassword, passwordHash)) {
            throw new InvalidPasswordException("Old password is incorrect");
        }
    }
}
