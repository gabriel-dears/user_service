package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.application.exception.TokenGenerationFailedException;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.login.dto.UserLoginRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.login.dto.UserLoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication", description = "Operations related to user login and authentication")
public interface UserLoginApi {

    @Operation(summary = "Login a user", description = "Authenticate user and return JWT token")
    @PostMapping("/login")
    default ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto)
            throws TokenGenerationFailedException {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
