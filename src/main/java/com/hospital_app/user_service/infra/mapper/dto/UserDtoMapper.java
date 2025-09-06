package com.hospital_app.user_service.infra.mapper.dto;

import com.hospital_app.common.db.pagination.ApplicationPage;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.CreateUserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.UpdateUserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.UserResponseByIdDto;
import com.hospital_app.user_service.infra.adapter.in.rest.controller.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserDtoMapper {

    public User toDomain(CreateUserRequestDto createUserRequestDto) {
        User user = new User();
        user.setName(createUserRequestDto.name());
        user.setCpf(createUserRequestDto.cpf());
        user.setRole(Role.valueOf(createUserRequestDto.role()));
        user.setUsername(createUserRequestDto.username());
        user.setEmail(createUserRequestDto.email());
        user.setPasswordHash(createUserRequestDto.password());
        return user;
    }

    public User toDomain(UpdateUserRequestDto updateUserRequestDto, UUID id) {
        User user = new User();
        user.setId(id);
        user.setCpf(updateUserRequestDto.cpf());
        user.setName(updateUserRequestDto.name());
        user.setRole(Role.valueOf(updateUserRequestDto.role()));
        user.setUsername(updateUserRequestDto.username());
        user.setEmail(updateUserRequestDto.email());
        return user;
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.isEnabled()
        );
    }


    public ApplicationPage<UserResponseDto> toResponseDto(ApplicationPage<User> userApplicationPage) {
        return new ApplicationPage<>(
                userApplicationPage.getPageNumber(),
                userApplicationPage.getPageSize(),
                userApplicationPage.getTotalPages(),
                userApplicationPage.getTotalElements(),
                userApplicationPage.isLast(),
                userApplicationPage.isFirst(),
                toResponseDto(userApplicationPage.getContent())
        );
    }

    public List<UserResponseDto> toResponseDto(List<User> content) {
        return content.stream().map(this::toResponseDto).toList();
    }

    public UserResponseByIdDto toResponseByIdDto(User createdUser) {
        return new UserResponseByIdDto(
                createdUser.getId(),
                createdUser.getName(),
                createdUser.getUsername(),
                createdUser.getEmail(),
                createdUser.getCpf(),
                createdUser.getRole().name(),
                createdUser.isEnabled()
        );
    }
}
