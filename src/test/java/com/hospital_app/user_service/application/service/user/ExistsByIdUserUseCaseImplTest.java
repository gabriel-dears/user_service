package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExistsByIdUserUseCaseImplTest {

    @InjectMocks
    private ExistsByIdUserUseCaseImpl existsByIdUserUseCase;

    @Mock
    private CustomUserRepository customUserRepository;

    @Test
    void shouldFindUser() {

        // Arrange

        UUID id = UUID.randomUUID();
        Role role = Role.DOCTOR;

        when(customUserRepository.existsByIdAndRole(any(UUID.class), any(Role.class))).thenReturn(true);

        // Act

        boolean exists = existsByIdUserUseCase.execute(id, role);

        // Assert

        assertTrue(exists);

    }

    @Test
    void shouldNotFindUser() {

        // Arrange

        UUID id = UUID.randomUUID();
        Role role = Role.DOCTOR;

        when(customUserRepository.existsByIdAndRole(any(UUID.class), any(Role.class))).thenReturn(false);

        // Act

        boolean exists = existsByIdUserUseCase.execute(id, role);

        // Assert

        assertFalse(exists);

    }

}