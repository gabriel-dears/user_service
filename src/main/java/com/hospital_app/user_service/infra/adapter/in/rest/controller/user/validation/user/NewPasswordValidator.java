package com.hospital_app.user_service.infra.adapter.in.rest.controller.user.validation.user;

import com.hospital_app.user_service.application.command.UserPasswordDetails;
import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.domain.exception.SamePasswordException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NewPasswordValidator implements InputValidator<UserPasswordDetails> {

    @Override
    public void validate(UserPasswordDetails userPasswordDetails) {
        String oldPassword = userPasswordDetails.oldPassword();
        String newPassword = userPasswordDetails.newPassword();
        if (Objects.equals(oldPassword, newPassword)) {
            throw new SamePasswordException("Old and new passwords are the same");
        }
    }
}
