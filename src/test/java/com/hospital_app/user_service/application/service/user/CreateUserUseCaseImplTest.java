package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.security.PasswordEncoderService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private CustomUserRepository customUserRepository;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @Mock
    private InputValidator<User> userInputValidator;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void shouldNotCreateUserWithAlreadyExistingUsername() {

        // Arrange

        doThrow(UsernameAlreadyExistsException.class).when(customUserRepository).create(any(User.class));

        // Act and Assert

        assertThrows(UsernameAlreadyExistsException.class, () -> createUserUseCase.execute(getUser()));

    }

    @Test
    void shouldNotCreateUserWithAlreadyExistingEmail() {

        // Arrange

        doThrow(EmailAlreadyExistsException.class).when(customUserRepository).create(any(User.class));

        // Act and Assert

        assertThrows(EmailAlreadyExistsException.class, () -> createUserUseCase.execute(getUser()));

    }

    @Test
    void shouldCreateUser() {

        // Arrange

        User user = getUser();

        doNothing().when(userInputValidator).validate(any(User.class));
        when(passwordEncoderService.encode(anyString())).thenReturn("#&$(*@#&$(*@#&$(*#@&(*$&@#$&(*#@$(*@#&(*$&(*@#&*$");
        when(customUserRepository.create(any(User.class))).thenReturn(user);

        // Act

        User createdUser = createUserUseCase.execute(user);

        // Assert

        assertEquals(user, createdUser);
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getRole(), createdUser.getRole());

    }

    private static User getUser() {
        User user = new User();
        user.setUsername("username");
        user.setPasswordHash("password_my_password");
        user.setEmail("email@email.com");
        user.setRole(Role.ADMIN);
        return user;
    }

}