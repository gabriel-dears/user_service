package com.hospital_app.user_service.application.exception;

public class UserDbException extends RuntimeException {
    public UserDbException(String message, Throwable cause) {
        super(message, cause);
    }
}
