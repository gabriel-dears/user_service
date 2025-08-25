package com.hospital_app.user_service.infra.config.user;

import com.hospital_app.user_service.application.port.in.user.CreateUserUseCase;
import com.hospital_app.user_service.application.port.in.user.FindAllUserUseCase;
import com.hospital_app.user_service.application.port.in.user.FindByIdUserUseCase;
import com.hospital_app.user_service.application.port.in.user.UpdateUserUseCase;
import com.hospital_app.user_service.application.port.in.validator.CompositeValidator;
import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.security.PasswordEncoderService;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.application.service.user.CreateUserUseCaseImpl;
import com.hospital_app.user_service.application.service.user.FindAllUserUseCaseImpl;
import com.hospital_app.user_service.application.service.user.FindByIdUserUseCaseImpl;
import com.hospital_app.user_service.application.service.user.UpdateUserUseCaseImpl;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user.UniqueEmailForAnotherIdValidator;
import com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user.UniqueEmailValidator;
import com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user.UniqueUsernameForAnotherIdValidator;
import com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user.UniqueUsernameValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
    FindAllUserUseCase findAllUserUseCase(CustomUserRepository customUserRepository) {
        return new FindAllUserUseCaseImpl(customUserRepository);
    }

    @Bean
    UpdateUserUseCase updateUserUseCase(CustomUserRepository customUserRepository, InputValidator<User> userInputValidator) {
        return new UpdateUserUseCaseImpl(customUserRepository, userInputValidator);
    }

    @Bean
    @Primary
    InputValidator<User> createUserInputValidator(
            UniqueEmailValidator uniqueEmailValidator,
            UniqueUsernameValidator uniqueUsernameValidator,
            UniqueEmailForAnotherIdValidator uniqueEmailForAnotherIdValidator,
            UniqueUsernameForAnotherIdValidator uniqueUsernameForAnotherIdValidator
    ) {
        return new CompositeValidator<>(Set.of(
                uniqueEmailValidator,
                uniqueUsernameValidator,
                uniqueEmailForAnotherIdValidator,
                uniqueUsernameForAnotherIdValidator
        ));
    }

}
