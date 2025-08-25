package com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user;

import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.EmailAlreadyExistsException;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user.helper.UserInputValidatorHelper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueEmailForAnotherIdValidator implements InputValidator<User> {

    private final CustomUserRepository customUserRepository;

    public UniqueEmailForAnotherIdValidator(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public void validate(User input) {
        String email = input.getEmail();
        UUID id = input.getId();
        if (UserInputValidatorHelper.isUpdateUserFlow(id) && customUserRepository.existsByEmailForAnotherId(email, id)) {
            throw new EmailAlreadyExistsException(String.format("Email %s already exists", email));
        }
    }

}
