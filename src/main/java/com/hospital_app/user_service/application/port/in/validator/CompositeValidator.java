package com.hospital_app.user_service.application.port.in.validator;

import java.util.Set;

public class CompositeValidator<T> implements InputValidator<T> {

    private final Set<InputValidator<T>> validators;

    public CompositeValidator(Set<InputValidator<T>> validators) {
        this.validators = validators;
    }

    @Override
    public void validate(T input) {
        validators.forEach(validator -> validator.validate(input));
    }
}
