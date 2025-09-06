package com.hospital_app.user_service.infra.adapter.in.rest.controller.user.validation.user;

import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.CpfAlreadyExistsException;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.validation.user.helper.UserInputValidatorHelper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueCpfForAnotherIdValidator implements InputValidator<User> {

    private final CustomUserRepository customUserRepository;

    public UniqueCpfForAnotherIdValidator(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public void validate(User input) {
        UUID id = input.getId();
        String cpf = input.getCpf();
        if (UserInputValidatorHelper.isUpdateUserFlow(id) && customUserRepository.existsByCpfForAnotherId(cpf, id)) {
            throw new CpfAlreadyExistsException(String.format("CPF %s already exists", cpf));
        }
    }

}
