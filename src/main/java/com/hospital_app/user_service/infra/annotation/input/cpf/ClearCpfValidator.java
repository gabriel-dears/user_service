package com.hospital_app.user_service.infra.annotation.input.cpf;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

public class ClearCpfValidator implements ConstraintValidator<ClearCpf, String> {

    private final CPFValidator cpfValidator = new CPFValidator();

    @Override
    public void initialize(ClearCpf constraintAnnotation) {
        cpfValidator.initialize(null);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        String cleaned = value.replaceAll("\\D", "");
        return cpfValidator.isValid(cleaned, context);
    }
}
