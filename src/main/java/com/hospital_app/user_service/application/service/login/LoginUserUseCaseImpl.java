package com.hospital_app.user_service.application.service.login;

import com.hospital_app.user_service.application.port.in.login.LoginUserUseCase;
import com.hospital_app.user_service.application.port.out.security.TokenService;

public class LoginUserUseCaseImpl implements LoginUserUseCase {

    private final TokenService tokenService;

    public LoginUserUseCaseImpl(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public String execute(String username, String password) {
        // TODO: Implement login logic
        return tokenService.generateToken(username, "ADMIN");
    }
}
