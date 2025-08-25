package com.hospital_app.user_service.application.command;

import java.util.UUID;

public record UserPasswordDetails(
        UUID userId,
        String oldPassword,
        String newPassword
) {
}
