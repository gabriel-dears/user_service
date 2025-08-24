package com.hospital_app.user_service.infra.adapter.out.security;

import com.hospital_app.user_service.application.port.out.security.PasswordEncoderService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptSpringPasswordEncoderService implements PasswordEncoderService {

    private final PasswordEncoder passwordEncoder;

    public BCryptSpringPasswordEncoderService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
