package com.hospital_app.user_service.infra.adapter.in.controller.user;

import com.hospital_app.user_service.application.command.UserPasswordDetails;
import com.hospital_app.user_service.application.common.pagination.ApplicationPage;
import com.hospital_app.user_service.application.port.in.user.*;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.ChangeUserPasswordRequestDto;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.CreateUserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UpdateUserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UserResponseDto;
import com.hospital_app.user_service.infra.mapper.dto.UserDtoMapper;
import com.hospital_app.user_service.infra.swagger.UserApi;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserRestController implements UserApi {

    private final FindByIdUserUseCase findByIdUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final FindAllUserUseCase findAllUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ChangeStatusUserUseCase changeStatusUserUseCase;
    private final ChangePasswordUserUseCase changePasswordUserUseCase;
    private final UserDtoMapper userDtoMapper;

    public UserRestController(FindByIdUserUseCase findByIdUserUseCase, CreateUserUseCase createUserUseCase, FindAllUserUseCase findAllUserUseCase, UpdateUserUseCase updateUserUseCase, ChangeStatusUserUseCase changeStatusUserUseCase, ChangePasswordUserUseCase changePasswordUserUseCase, UserDtoMapper userDtoMapper) {
        this.findByIdUserUseCase = findByIdUserUseCase;
        this.createUserUseCase = createUserUseCase;
        this.findAllUserUseCase = findAllUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.changeStatusUserUseCase = changeStatusUserUseCase;
        this.changePasswordUserUseCase = changePasswordUserUseCase;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id) {
        User user = findByIdUserUseCase.execute(id);
        return ResponseEntity.ok(userDtoMapper.toResponseDto(user));
    }

    @PostMapping
    @Override
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid CreateUserRequestDto createUserRequestDto) {
        User domain = userDtoMapper.toDomain(createUserRequestDto);
        User createdUser = createUserUseCase.execute(domain);
        return ResponseEntity.ok(userDtoMapper.toResponseDto(createdUser));
    }

    @GetMapping
    @Override
    public ResponseEntity<ApplicationPage<UserResponseDto>> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(userDtoMapper.toResponseDto(findAllUserUseCase.execute(pageNumber, pageSize)));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        // TODO: implement update and change user status
        // TODO: tests
        // TODO: send values as env variables -> docker compose
        // TODO: create insomnia tests collection
        // TODO: handle errors
        // TODO: add swagger docs
        // TODO: create appointment service
        // TODO: remover oauth client do common security
        // TODO: move private key to the user service only
        // TODO: move JwtUtils to the user service only (user-service - generates the token and validates the token, appointments-service - validates the token)
        User updatedUser = updateUserUseCase.execute(userDtoMapper.toDomain(updateUserRequestDto, id));
        return ResponseEntity.ok(userDtoMapper.toResponseDto(updatedUser));
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<Void> changeStatus(@PathVariable UUID id, @RequestParam boolean enabled) {
        changeStatusUserUseCase.execute(id, enabled);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/change-password")
    @Override
    public ResponseEntity<Void> changePassword(@PathVariable UUID id, @RequestBody @Valid ChangeUserPasswordRequestDto changeUserPasswordRequestDto) {
        String newPassword = changeUserPasswordRequestDto.newPassword();
        String oldPassword = changeUserPasswordRequestDto.oldPassword();
        UserPasswordDetails userPasswordDetails = new UserPasswordDetails(id, oldPassword, newPassword);
        changePasswordUserUseCase.execute(userPasswordDetails);
        return ResponseEntity.ok().build();
    }

}
