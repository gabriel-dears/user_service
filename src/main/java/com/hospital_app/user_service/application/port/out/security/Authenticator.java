package com.hospital_app.user_service.application.port.out.security;

import com.hospital_app.user_service.application.port.out.security.dto.AuthDetailsDto;

public interface Authenticator {
    AuthDetailsDto authenticate(String username, String password);
}
