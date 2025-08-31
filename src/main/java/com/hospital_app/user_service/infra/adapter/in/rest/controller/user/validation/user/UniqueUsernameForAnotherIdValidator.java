package com.hospital_app.user_service.infra.adapter.in.rest.controller.user.validation.user;

import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.UsernameAlreadyExistsException;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.validation.user.helper.UserInputValidatorHelper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueUsernameForAnotherIdValidator implements InputValidator<User> {

    private final CustomUserRepository customUserRepository;

    public UniqueUsernameForAnotherIdValidator(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public void validate(User input) {
        String email = input.getEmail();
        UUID id = input.getId();
        if (UserInputValidatorHelper.isUpdateUserFlow(id) && customUserRepository.existsByUsernameForAnotherId(email, id)) {
            throw new UsernameAlreadyExistsException(String.format("Username %s already exists", email));
        }
    }

}
