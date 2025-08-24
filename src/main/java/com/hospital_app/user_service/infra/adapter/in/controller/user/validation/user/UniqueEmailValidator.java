package com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user;

import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.EmailAlreadyExistsException;
import com.hospital_app.user_service.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements InputValidator<User> {

    private final CustomUserRepository customUserRepository;

    public UniqueEmailValidator(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public void validate(User user) {
        String email = user.getEmail();
        if (customUserRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(String.format("Email %s already exists", email));
        }
    }
}
