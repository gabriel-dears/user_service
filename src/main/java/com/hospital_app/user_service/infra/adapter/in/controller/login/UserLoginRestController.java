package com.hospital_app.user_service.infra.adapter.in.controller.login;

import com.hospital_app.jwt_security_common.infra.utils.JwtUtils;
import com.hospital_app.user_service.infra.adapter.in.controller.login.dto.UserLoginRequestDto;
import com.hospital_app.user_service.infra.adapter.in.controller.login.dto.UserLoginResponseDto;
import com.hospital_app.user_service.infra.swagger.UserLoginApi;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/login")
public class UserLoginRestController implements UserLoginApi {

    private final JwtUtils jwtUtils;

    public UserLoginRestController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, JOSEException {
        var jwtToken = jwtUtils.generateToken("mockuser", "ADMIN");
        return ResponseEntity.ok(new UserLoginResponseDto(jwtToken));
    }

}
