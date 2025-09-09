package com.hospital_app.user_service.infra.config;

import com.hospital_app.user_service.application.port.in.user.CreateUserUseCase;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdminUser(
            @Value("${INITIAL_ADMIN_NAME}") String initialAdminUserName,
            @Value("${INITIAL_ADMIN_USERNAME}") String initialAdminUserUsername,
            @Value("${INITIAL_ADMIN_EMAIL}") String initialAdminUserEmail,
            @Value("${INITIAL_ADMIN_CPF}") String initialAdminUserCpf,
            @Value("${INITIAL_ADMIN_PASSWORD}") String initialAdminUserPassword,
            CreateUserUseCase createUserUseCase,
            CustomUserRepository customUserRepository) {
        return args -> {

            if(customUserRepository.existsByUsername(initialAdminUserUsername)) {
                return;
            }

            User user = new User();
            user.setName(initialAdminUserName);
            user.setUsername(initialAdminUserUsername);
            user.setEmail(initialAdminUserEmail);
            user.setCpf(initialAdminUserCpf);
            user.setPasswordHash(initialAdminUserPassword);
            user.setRole(Role.ADMIN);
            createUserUseCase.execute(user);
        };
    }

}
