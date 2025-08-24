package com.hospital_app.user_service.application.service.login;

import com.hospital_app.user_service.application.port.in.login.LoginUserUseCase;
import com.hospital_app.user_service.application.port.out.security.Authenticator;
import com.hospital_app.user_service.application.port.out.security.TokenService;
import com.hospital_app.user_service.application.port.out.security.dto.AuthDetailsDto;

public class LoginUserUseCaseImpl implements LoginUserUseCase {

    private final TokenService tokenService;
    private final Authenticator authenticator;

    public LoginUserUseCaseImpl(TokenService tokenService, Authenticator authenticator) {
        this.tokenService = tokenService;
        this.authenticator = authenticator;
    }

    @Override
    public String execute(String username, String password) {
        AuthDetailsDto authDetailsDto = authenticator.authenticate(username, password);
        return tokenService.generateToken(authDetailsDto.username(), authDetailsDto.role());
    }
}
