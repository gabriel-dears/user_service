package com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user;

import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.UsernameAlreadyExistsException;
import com.hospital_app.user_service.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UniqueUsernameValidator implements InputValidator<User> {

    private final CustomUserRepository customUserRepository;

    public UniqueUsernameValidator(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public void validate(User user) {
        String username = user.getUsername();
        if (customUserRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException(String.format("Username %s already exists", username));
        }
    }
}
