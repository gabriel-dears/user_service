package com.hospital_app.user_service.infra.adapter.in.rest.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital_app.common.db.pagination.ApplicationPage;
import com.hospital_app.user_service.application.command.UserPasswordDetails;
import com.hospital_app.user_service.application.port.in.user.*;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.ChangeUserPasswordRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.CreateUserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.UpdateUserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.UserResponseDto;
import com.hospital_app.user_service.infra.mapper.dto.UserDtoMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserRestControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final FindByIdUserUseCase findByIdUserUseCase = org.mockito.Mockito.mock(FindByIdUserUseCase.class);
    private final CreateUserUseCase createUserUseCase = org.mockito.Mockito.mock(CreateUserUseCase.class);
    private final FindAllUserUseCase findAllUserUseCase = org.mockito.Mockito.mock(FindAllUserUseCase.class);
    private final UpdateUserUseCase updateUserUseCase = org.mockito.Mockito.mock(UpdateUserUseCase.class);
    private final ChangeStatusUserUseCase changeStatusUserUseCase = org.mockito.Mockito.mock(ChangeStatusUserUseCase.class);
    private final ChangePasswordUserUseCase changePasswordUserUseCase = org.mockito.Mockito.mock(ChangePasswordUserUseCase.class);
    private final UserDtoMapper userDtoMapper = org.mockito.Mockito.mock(UserDtoMapper.class);

    @org.junit.jupiter.api.BeforeEach
    void setup() {
        UserRestController controller = new UserRestController(
                findByIdUserUseCase,
                createUserUseCase,
                findAllUserUseCase,
                updateUserUseCase,
                changeStatusUserUseCase,
                changePasswordUserUseCase,
                userDtoMapper
        );
        this.mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getUserById_returnsOkWithDto() throws Exception {
        UUID id = UUID.randomUUID();
        User domain = new User();
        domain.setId(id);
        domain.setName("John");
        domain.setUsername("john");
        domain.setEmail("john@example.com");
        domain.setRole(Role.ADMIN);
        domain.setEnabled(true);
        UserResponseDto responseDto = new UserResponseDto(id, "John", "john", "john@example.com", "ADMIN", true);

        when(findByIdUserUseCase.execute(id)).thenReturn(domain);
        when(userDtoMapper.toResponseDto(domain)).thenReturn(responseDto);

        mockMvc.perform(get("/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.username", is("john")))
                .andExpect(jsonPath("$.email", is("john@example.com")))
                .andExpect(jsonPath("$.role", is("ADMIN")))
                .andExpect(jsonPath("$.enabled", is(true)));
    }

    @Test
    void createUser_returnsCreatedWithLocationAndBody() throws Exception {
        CreateUserRequestDto request = new CreateUserRequestDto("Mary", "mary01", "mary@example.com", "477.278.470-54", "verysecurepwd", "PATIENT");
        User toCreate = new User();
        toCreate.setName("Mary");
        toCreate.setUsername("mary01");
        toCreate.setEmail("mary@example.com");
        toCreate.setPasswordHash("verysecurepwd");
        toCreate.setRole(Role.PATIENT);

        UUID id = UUID.randomUUID();
        User created = new User();
        created.setId(id);
        created.setName("Mary");
        created.setUsername("mary01");
        created.setEmail("mary@example.com");
        created.setRole(Role.PATIENT);
        created.setEnabled(true);

        UserResponseDto responseDto = new UserResponseDto(id, "Mary", "mary01", "mary@example.com", "PATIENT", true);

        when(userDtoMapper.toDomain(org.mockito.ArgumentMatchers.any(CreateUserRequestDto.class))).thenReturn(toCreate);
        when(createUserUseCase.execute(toCreate)).thenReturn(created);
        when(userDtoMapper.toResponseDto(created)).thenReturn(responseDto);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/user/" + id)))
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.name", is("Mary")));
    }

    @Test
    void findAll_returnsPageOfDtos() throws Exception {
        int pageNumber = 0;
        int pageSize = 2;
        ApplicationPage<User> domainPage = new ApplicationPage<>(pageNumber, pageSize, 1, 2, true, true, List.of(new User(), new User()));
        ApplicationPage<UserResponseDto> dtoPage = new ApplicationPage<>(pageNumber, pageSize, 1, 2, true, true,
                List.of(new UserResponseDto(UUID.randomUUID(), "n1", "u1", "e1", "ADMIN", true),
                        new UserResponseDto(UUID.randomUUID(), "n2", "u2", "e2", "PATIENT", false)));

        when(findAllUserUseCase.execute(pageNumber, pageSize)).thenReturn(domainPage);
        when(userDtoMapper.toResponseDto(domainPage)).thenReturn(dtoPage);

        mockMvc.perform(get("/user")
                        .param("pageNumber", String.valueOf(pageNumber))
                        .param("pageSize", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber", is(pageNumber)))
                .andExpect(jsonPath("$.pageSize", is(pageSize)))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void updateUser_returnsOkWithBody() throws Exception {
        UUID id = UUID.randomUUID();
        UpdateUserRequestDto request = new UpdateUserRequestDto("New Name", "newuser", "new@example.com", "477.278.470-54", "NURSE");
        User toUpdate = new User();
        toUpdate.setId(id);
        toUpdate.setName("New Name");
        toUpdate.setUsername("newuser");
        toUpdate.setEmail("new@example.com");
        toUpdate.setRole(Role.NURSE);

        User updated = new User();
        updated.setId(id);
        updated.setName("New Name");
        updated.setUsername("newuser");
        updated.setEmail("new@example.com");
        updated.setRole(Role.NURSE);
        updated.setEnabled(true);

        UserResponseDto responseDto = new UserResponseDto(id, "New Name", "newuser", "new@example.com", "NURSE", true);

        when(userDtoMapper.toDomain(org.mockito.ArgumentMatchers.any(UpdateUserRequestDto.class), eq(id))).thenReturn(toUpdate);
        when(updateUserUseCase.execute(toUpdate)).thenReturn(updated);
        when(userDtoMapper.toResponseDto(updated)).thenReturn(responseDto);

        mockMvc.perform(put("/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.name", is("New Name")));
    }

    @Test
    void changeStatus_returnsOk_andInvokesUseCase() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(patch("/user/{id}", id).param("enabled", "true"))
                .andExpect(status().isOk());

        then(changeStatusUserUseCase).should().execute(id, true);
    }

    @Test
    void changePassword_returnsOk_andInvokesUseCaseWithCorrectDetails() throws Exception {
        UUID id = UUID.randomUUID();
        ChangeUserPasswordRequestDto request = new ChangeUserPasswordRequestDto("oldpassword1", "newpassword2");

        mockMvc.perform(patch("/user/{id}/change-password", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        ArgumentCaptor<UserPasswordDetails> captor = ArgumentCaptor.forClass(UserPasswordDetails.class);
        then(changePasswordUserUseCase).should().execute(captor.capture());
        UserPasswordDetails details = captor.getValue();
        // Validate captured details
        org.junit.jupiter.api.Assertions.assertEquals(id, details.userId());
        org.junit.jupiter.api.Assertions.assertEquals("oldpassword1", details.oldPassword());
        org.junit.jupiter.api.Assertions.assertEquals("newpassword2", details.newPassword());
    }
}
