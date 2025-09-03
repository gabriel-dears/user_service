package com.hospital_app.user_service.application.port.out.security.dto;

import java.util.UUID;

public record AuthDetailsDto(
        String username,
        String role,
        UUID id) {
}
