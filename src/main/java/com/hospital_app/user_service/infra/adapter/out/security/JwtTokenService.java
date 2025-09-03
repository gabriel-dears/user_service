package com.hospital_app.user_service.infra.adapter.out.security;

import com.hospital_app.user_service.application.exception.TokenGenerationFailedException;
import com.hospital_app.user_service.application.port.out.security.TokenService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JwtTokenService implements TokenService {

    private final JwtUtils jwtUtils;

    public JwtTokenService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String generateToken(String username, String role, UUID userId) throws TokenGenerationFailedException {
        try {
            return jwtUtils.generateToken(username, role, userId);
        } catch (Exception e) {
            throw new TokenGenerationFailedException("Error while generating token!", e);
        }
    }
}
