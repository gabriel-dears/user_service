package com.hospital_app.user_service.application.port.out.security;

import com.hospital_app.user_service.application.exception.TokenGenerationFailedException;

import java.util.UUID;

public interface TokenService {
    String generateToken(String username, String role, UUID userId) throws TokenGenerationFailedException;
}
