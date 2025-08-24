package com.hospital_app.user_service.infra.adapter.in.controller.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotBlank
        @Size(min = 5, max = 50)
        String username,
        @NotBlank
        @Email
        @Size(max = 100)
        String email,
        @NotBlank
        @Size(min = 10, max = 50)
        String password,
        @Pattern(regexp = "(ADMIN|USER|NURSE|PATIENT)", message = "Invalid role")
        String role
) {
}