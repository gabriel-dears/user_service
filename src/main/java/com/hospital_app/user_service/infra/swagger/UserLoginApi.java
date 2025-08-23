package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.infra.adapter.in.controller.login.dto.UserLoginRequestDto;
import com.hospital_app.user_service.infra.adapter.in.controller.login.dto.UserLoginResponseDto;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UserLoginApi {

    default ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, JOSEException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
