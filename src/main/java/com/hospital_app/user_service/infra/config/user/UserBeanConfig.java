package com.hospital_app.user_service.infra.config.user;

import com.hospital_app.user_service.application.port.in.FindByIdUserUseCase;
import com.hospital_app.user_service.application.port.out.CustomUserRepository;
import com.hospital_app.user_service.application.service.FindByIdUserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfig {

    @Bean
    FindByIdUserUseCase findByIdUserUseCase(CustomUserRepository customUserRepository) {
        return new FindByIdUserUseCaseImpl(customUserRepository);
    }
}
