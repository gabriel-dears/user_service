package com.hospital_app.user_service.infra.config.user;

import com.hospital_app.user_service.application.command.UserPasswordDetails;
import com.hospital_app.user_service.application.port.in.user.*;
import com.hospital_app.user_service.application.port.in.validator.CompositeValidator;
import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.security.PasswordEncoderService;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.application.service.user.*;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.validation.user.*;
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
    ChangeStatusUserUseCase changeStatusUserUseCase(CustomUserRepository customUserRepository) {
        return new ChangeStatusUserUseCaseImpl(customUserRepository);
    }

    @Bean
    ChangePasswordUserUseCase changePasswordUserUseCase(CustomUserRepository customUserRepository, PasswordEncoderService passwordEncoderService, InputValidator<UserPasswordDetails> userInputValidator) {
        return new ChangePasswordUserUseCaseImpl(customUserRepository, passwordEncoderService, userInputValidator);
    }

    @Bean
    ExistsByIdUserUseCase existsByIdUserUseCase(CustomUserRepository customUserRepository) {
        return new ExistsByIdUserUseCaseImpl(customUserRepository);
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

    @Bean
    @Primary
    InputValidator<UserPasswordDetails> createUserPasswordInputValidator(
            CorrectPasswordValidator correctPasswordValidator,
            NewPasswordValidator newPasswordValidator
    ) {
        return new CompositeValidator<>(Set.of(
                correctPasswordValidator,
                newPasswordValidator
        ));
    }

}
