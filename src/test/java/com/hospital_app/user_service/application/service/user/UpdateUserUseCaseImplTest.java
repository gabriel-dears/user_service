package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.EmailAlreadyExistsException;
import com.hospital_app.user_service.domain.exception.UsernameAlreadyExistsException;
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
class UpdateUserUseCaseImplTest {

    @Mock
    private CustomUserRepository customUserRepository;

    @Mock
    private InputValidator<User> userInputValidator;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void shouldNotUpdateUserWhenEmailAlreadyExistsForAnotherUser() {

        // Arrange

        User user = getUser();
        doThrow(EmailAlreadyExistsException.class).when(userInputValidator).validate(any(User.class));

        // Act and Assert

        assertThrows(EmailAlreadyExistsException.class, () -> updateUserUseCase.execute(user));

    }

    @Test
    void shouldNotUpdateUserWhenUsernameAlreadyExistsForAnotherUser() {

        // Arrange

        User user = getUser();
        doThrow(UsernameAlreadyExistsException.class).when(userInputValidator).validate(any(User.class));

        // Act and Assert

        assertThrows(UsernameAlreadyExistsException.class, () -> updateUserUseCase.execute(user));

    }

    @Test
    void shouldUpdateUser() {

        // Arrange

        User user = getUser();
        doNothing().when(userInputValidator).validate(any(User.class));
        when(customUserRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(customUserRepository.update(any(User.class))).thenReturn(user);

        // Act

        User updatedUser = updateUserUseCase.execute(user);

        // Assert
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(user.getRole(), updatedUser.getRole());
    }

    private static User getUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("username_update");
        user.setEmail("email_update@email.com");
        user.setRole(Role.ADMIN);
        return user;
    }

}