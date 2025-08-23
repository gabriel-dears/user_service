package com.hospital_app.user_service.application.service;

import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.application.service.user.FindByIdUserUseCaseImpl;
import com.hospital_app.user_service.domain.exception.UserNotFoundException;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindByIdUserUseCaseImplTest {

    @Mock
    private CustomUserRepository customUserRepository;

    @InjectMocks
    private FindByIdUserUseCaseImpl findByIdUserUseCase;

    @Test
    void shouldNotFindUserById() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        when(customUserRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> findByIdUserUseCase.execute(uuid));
        verify(customUserRepository, times(1)).findById(uuid);
    }

    @Test
    void shouldFindUserById() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        User user = getUser(uuid);
        when(customUserRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        // Act
        User createdUser = findByIdUserUseCase.execute(uuid);
        // Assert
        assertEquals(createdUser.getId(), uuid);
        assertEquals(createdUser.getUsername(), user.getUsername());
        assertEquals(createdUser.getRole(), user.getRole());
        assertEquals(createdUser.isEnabled(), user.isEnabled());
        verify(customUserRepository, times(1)).findById(uuid);
    }

    private User getUser(UUID uuid) {
        User user = new User();
        user.setId(uuid);
        user.setUsername("username");
        user.setEnabled(true);
        user.setRole(Role.DOCTOR);
        return user;
    }


}