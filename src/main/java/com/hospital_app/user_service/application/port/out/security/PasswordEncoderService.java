package com.hospital_app.user_service.application.port.out.security;

public interface PasswordEncoderService {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
