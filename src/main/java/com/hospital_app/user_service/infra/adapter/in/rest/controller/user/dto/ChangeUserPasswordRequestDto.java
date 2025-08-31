package com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeUserPasswordRequestDto(
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 10, max = 50, message = "Old password length must be between 10 and 50 characters")
        String oldPassword,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 10, max = 50, message = "New password length must be between 10 and 50 characters")
        String newPassword
) {
}
