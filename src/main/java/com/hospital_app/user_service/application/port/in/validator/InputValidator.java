package com.hospital_app.user_service.application.port.in.validator;

public interface InputValidator<T> {
    void validate(T input);
}
