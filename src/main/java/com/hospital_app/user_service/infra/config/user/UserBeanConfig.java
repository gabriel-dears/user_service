package com.hospital_app.user_service.infra.config.user;

import com.hospital_app.user_service.application.port.in.user.CreateUserUseCase;
import com.hospital_app.user_service.application.port.in.user.FindByIdUserUseCase;
import com.hospital_app.user_service.application.port.in.validator.CompositeValidator;
import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.security.PasswordEncoderService;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.application.service.user.CreateUserUseCaseImpl;
import com.hospital_app.user_service.application.service.user.FindByIdUserUseCaseImpl;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user.UniqueEmailValidator;
import com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user.UniqueUsernameValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class UserBeanConfig {

    @Bean
    FindByIdUserUseCase findByIdUserUseCase(CustomUserRepository customUserRepository) {
        return new FindByIdUserUseCaseImpl(customUserRepository);
    }

    @Bean
    CreateUserUseCase createUserUseCase(CustomUserRepository customUserRepository, PasswordEncoderService passwordEncoderService, InputValidator<User> userInputValidator) {
        return new CreateUserUseCaseImpl(customUserRepository, passwordEncoderService, userInputValidator);
    }

    @Bean
    InputValidator<User> userInputValidator(
            UniqueEmailValidator uniqueEmailValidator,
            UniqueUsernameValidator uniqueUsernameValidator
    ) {
        return new CompositeValidator<>(Set.of(uniqueEmailValidator, uniqueUsernameValidator));
    }

}
