package com.hospital_app.user_service.application.exception;

public class TokenGenerationFailedException extends RuntimeException {
    public TokenGenerationFailedException(String message, Exception e) {
        super(message, e);
    }
}
