package com.hospital_app.user_service.application.port.out.security;

import com.hospital_app.user_service.application.exception.TokenGenerationFailedException;

public interface TokenService {
    String generateToken(String username, String role) throws TokenGenerationFailedException;
}
