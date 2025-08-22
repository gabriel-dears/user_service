package com.hospital_app.user_service.infra.adapter.in.controller.user.dto;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String username,
        String role,
        boolean enabled
) {
}
