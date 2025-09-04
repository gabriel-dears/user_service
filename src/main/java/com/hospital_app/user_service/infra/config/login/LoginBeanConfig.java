package com.hospital_app.user_service.infra.config.login;

import com.hospital_app.user_service.application.port.in.login.LoginUserUseCase;
import com.hospital_app.user_service.application.port.out.security.Authenticator;
import com.hospital_app.user_service.application.port.out.security.TokenService;
import com.hospital_app.user_service.application.service.login.LoginUserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginBeanConfig {

    // TODO - filter with email
    @Bean
    LoginUserUseCase loginUserUseCase(TokenService tokenService, Authenticator authenticator) {
        return new LoginUserUseCaseImpl(tokenService, authenticator);
    }

}
