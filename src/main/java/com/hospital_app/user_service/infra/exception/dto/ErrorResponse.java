package com.hospital_app.user_service.infra.exception.dto;

public record ErrorResponse(
        String message,
        String timestamp,
        int status,
        String path
) {
}
