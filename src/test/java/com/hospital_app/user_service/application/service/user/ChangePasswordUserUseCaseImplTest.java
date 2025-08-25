package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.command.UserPasswordDetails;
import com.hospital_app.user_service.application.port.in.validator.InputValidator;
import com.hospital_app.user_service.application.port.out.security.PasswordEncoderService;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.exception.InvalidPasswordException;
import com.hospital_app.user_service.domain.exception.SamePasswordException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangePasswordUserUseCaseImplTest {

    @InjectMocks
    private ChangePasswordUserUseCaseImpl changePasswordUserUseCase;

    @Mock
    private CustomUserRepository customUserRepository;

    @Mock
    private InputValidator<UserPasswordDetails> userInputValidator;

    @Mock
    private PasswordEncoderService passwordEncoderService;

    @Test
    void shouldNotChangePasswordBecauseOldAndNewPasswordsAreTheSame() {

        // Arrange

        doThrow(SamePasswordException.class).when(userInputValidator).validate(any(UserPasswordDetails.class));

        var userPasswordDetails = new UserPasswordDetails(
                UUID.randomUUID(),
                "oldPass",
                "newPass"
        );

        // Act and Assert

        assertThrows(SamePasswordException.class, () -> changePasswordUserUseCase.execute(userPasswordDetails));

        verify(userInputValidator, times(1)).validate(any(UserPasswordDetails.class));
        verify(passwordEncoderService, never()).encode(anyString());
        verify(customUserRepository, never()).updateUserPassword(anyString(), any(UUID.class));

    }

    @Test
    void shouldNotChangePasswordBecauseOldPasswordIsInvalid() {

        // Arrange

        doThrow(InvalidPasswordException.class).when(userInputValidator).validate(any(UserPasswordDetails.class));

        var userPasswordDetails = new UserPasswordDetails(
                UUID.randomUUID(),
                "oldPass",
                "newPass"
        );

        // Act and Assert

        assertThrows(InvalidPasswordException.class, () -> changePasswordUserUseCase.execute(userPasswordDetails));

        verify(userInputValidator, times(1)).validate(any(UserPasswordDetails.class));
        verify(passwordEncoderService, never()).encode(anyString());
        verify(customUserRepository, never()).updateUserPassword(anyString(), any(UUID.class));

    }

    @Test
    void shouldChangePassword() {

        // Arrange

        doNothing().when(userInputValidator).validate(any(UserPasswordDetails.class));
        when(passwordEncoderService.encode(anyString())).thenReturn("encodedNewPass");
        doNothing().when(customUserRepository).updateUserPassword(anyString(), any(UUID.class));

        var userPasswordDetails = new UserPasswordDetails(
                UUID.randomUUID(),
                "oldPass",
                "newPass"
        );

        // Act

        changePasswordUserUseCase.execute(userPasswordDetails);

        // Assert

        verify(userInputValidator, times(1)).validate(any(UserPasswordDetails.class));
        verify(passwordEncoderService, times(1)).encode(anyString());
        verify(customUserRepository, times(1)).updateUserPassword(anyString(), any(UUID.class));

    }

}