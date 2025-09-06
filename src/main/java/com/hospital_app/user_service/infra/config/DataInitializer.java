package com.hospital_app.user_service.infra.config;

import com.hospital_app.user_service.application.port.in.user.CreateUserUseCase;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdminUser(CreateUserUseCase createUserUseCase, CustomUserRepository customUserRepository) {
        return args -> {

            if( customUserRepository.existsByUsername("admin") ) {
                return;
            }

            User user = new User();
            user.setName("Admin");
            user.setUsername("admin");
            user.setEmail("admin@email.com");
            user.setCpf("063.086.090-48");
            user.setPasswordHash("admin");
            user.setRole(Role.ADMIN);
            createUserUseCase.execute(user);
        };
    }

}
