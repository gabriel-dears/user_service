package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeStatusUserUseCaseImplTest {

    @Mock
    private CustomUserRepository customUserRepository;

    @InjectMocks
    private ChangeStatusUserUseCaseImpl changeStatusUserUseCase;

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        // Arrange

        when(customUserRepository.existsById(any(UUID.class))).thenReturn(false);

        // Act and Assert

        assertThrows(UserNotFoundException.class, () -> changeStatusUserUseCase.execute(UUID.randomUUID(), true));

    }

    @Test
    void shouldDisableUser() {

        // Arrange
        UUID id = UUID.randomUUID();
        boolean enabled = false;
        when(customUserRepository.existsById(any(UUID.class))).thenReturn(true);

        // Act

        changeStatusUserUseCase.execute(id, enabled);

        // Assert

        verify(customUserRepository, times(1)).existsById(any(UUID.class));
        verify(customUserRepository, times(1)).changeUserStatus(any(UUID.class), any(boolean.class));

    }

    @Test
    void shouldEnableUser() {
        // Arrange
        UUID id = UUID.randomUUID();
        boolean enabled = true;
        when(customUserRepository.existsById(any(UUID.class))).thenReturn(true);

        // Act

        changeStatusUserUseCase.execute(id, enabled);

        // Assert

        verify(customUserRepository, times(1)).existsById(any(UUID.class));
        verify(customUserRepository, times(1)).changeUserStatus(any(UUID.class), any(boolean.class));
    }


}