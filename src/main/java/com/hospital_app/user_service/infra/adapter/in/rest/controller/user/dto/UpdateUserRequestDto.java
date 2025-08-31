package com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 255, message = "Name length must be between 3 and 255 characters")
        String name,
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 5, max = 50, message = "Username length must be between 5 and 50 characters")
        String username,
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email")
        @Size(max = 100, message = "Email length must be less than 100 characters")
        String email,
        @Pattern(regexp = "(ADMIN|USER|NURSE|PATIENT)", message = "Invalid role")
        String role
) {
}