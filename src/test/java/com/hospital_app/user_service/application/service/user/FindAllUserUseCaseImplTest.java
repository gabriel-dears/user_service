package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.common.pagination.ApplicationPage;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllUserUseCaseImplTest {

    @InjectMocks
    private FindAllUserUseCaseImpl findAllUserUseCase;

    @Mock
    private CustomUserRepository customUserRepository;

    @Test
    void shouldReturnEmptyList() {

        // Arrange

        int pageNumber = 0;
        int pageSize = 10;

        when(customUserRepository.findAll(anyInt(), anyInt())).thenReturn(new ApplicationPage<>(
                pageNumber,
                pageSize,
                1,
                0,
                true,
                true,
                List.of()
        ));

        // Act

        ApplicationPage<User> userApplicationPage = findAllUserUseCase.execute(pageNumber, pageSize);

        // Assert

        assertTrue(userApplicationPage.getContent().isEmpty());

    }

    @Test
    void shouldReturnUserList() {

        // Arrange

        int pageNumber = 0;
        int pageSize = 10;

        when(customUserRepository.findAll(anyInt(), anyInt())).thenReturn(new ApplicationPage<>(
                pageNumber,
                pageSize,
                1,
                10,
                true,
                true,
                List.of(
                        new User(),
                        new User(),
                        new User(),
                        new User(),
                        new User(),
                        new User(),
                        new User(),
                        new User(),
                        new User(),
                        new User()
                )
        ));

        // Act

        ApplicationPage<User> userApplicationPage = findAllUserUseCase.execute(pageNumber, pageSize);

        // Assert

        assertEquals(10, userApplicationPage.getContent().size());

    }

}