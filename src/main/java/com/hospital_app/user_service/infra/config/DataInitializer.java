package com.hospital_app.user_service.infra.config;

import com.hospital_app.user_service.application.port.in.user.CreateUserUseCase;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdminUser(CreateUserUseCase createUserUseCase) {
        return args -> {
            User user = new User();
            user.setName("Admin");
            user.setUsername("admin");
            user.setEmail("admin@email.com");
            user.setPasswordHash("admin");
            user.setRole(Role.ADMIN);
            createUserUseCase.execute(user);
        };
    }

}
