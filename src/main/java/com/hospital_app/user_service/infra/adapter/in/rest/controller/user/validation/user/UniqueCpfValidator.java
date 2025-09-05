package com.hospital_app.user_service.infra.adapter.in.rest.controller.user.validation.user;

import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.CpfAlreadyExistsException;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.validation.user.helper.UserInputValidatorHelper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueCpfValidator implements InputValidator<User> {

    private final CustomUserRepository customUserRepository;

    public UniqueCpfValidator(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public void validate(User user) {
        UUID id = user.getId();
        String cpf = user.getCpf();
        if (UserInputValidatorHelper.isCreateUserFlow(id) && customUserRepository.existsByCpf(cpf)) {
            throw new CpfAlreadyExistsException(String.format("CPF %s already exists", cpf));
        }
    }

}
