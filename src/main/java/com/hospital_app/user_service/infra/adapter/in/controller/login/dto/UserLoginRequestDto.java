package com.hospital_app.user_service.infra.adapter.in.controller.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank
        @Size(min = 5, max = 50)
        String user,
        @NotBlank
        @Size(min = 10, max = 50)
        String password
) {
}
