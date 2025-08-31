package com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String username,
        String email,
        String role,
        boolean enabled
        ) {
}
