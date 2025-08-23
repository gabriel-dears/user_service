package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.application.exception.TokenGenerationFailedException;
import com.hospital_app.user_service.infra.adapter.in.controller.login.dto.UserLoginRequestDto;
import com.hospital_app.user_service.infra.adapter.in.controller.login.dto.UserLoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserLoginApi {

    default ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) throws TokenGenerationFailedException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
