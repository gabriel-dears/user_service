package com.hospital_app.user_service.infra.adapter.in.controller.login;

import com.hospital_app.user_service.application.exception.TokenGenerationFailedException;
import com.hospital_app.user_service.application.port.in.login.LoginUserUseCase;
import com.hospital_app.user_service.infra.adapter.in.controller.login.dto.UserLoginRequestDto;
import com.hospital_app.user_service.infra.adapter.in.controller.login.dto.UserLoginResponseDto;
import com.hospital_app.user_service.infra.swagger.UserLoginApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserLoginRestController implements UserLoginApi {

    private final LoginUserUseCase loginUserUseCase;

    public UserLoginRestController(LoginUserUseCase loginUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) throws TokenGenerationFailedException {
        var user = userLoginRequestDto.user();
        var password = userLoginRequestDto.password();
        var jwtToken = loginUserUseCase.execute(user, password);
        return ResponseEntity.ok(new UserLoginResponseDto(jwtToken));
    }

}
