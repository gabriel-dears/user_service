package com.hospital_app.user_service.application.port.out.security.dto;

public record AuthDetailsDto(
        String username,
        String role
) {
}
