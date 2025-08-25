package com.hospital_app.user_service.infra.mapper.dto;

import com.hospital_app.user_service.application.common.pagination.ApplicationPage;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDtoMapper {

    public User toDomain(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.name());
        user.setRole(Role.valueOf(userRequestDto.role()));
        user.setUsername(userRequestDto.username());
        user.setEmail(userRequestDto.email());
        user.setPasswordHash(userRequestDto.password());
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
                userApplicationPage.getGetPageNumber(),
                userApplicationPage.getGetPageSize(),
                userApplicationPage.getGetTotalPages(),
                userApplicationPage.getGetTotalElements(),
                userApplicationPage.isLast(),
                userApplicationPage.isFirst(),
                toResponseDto(userApplicationPage.getContent())
        );
    }

    public List<UserResponseDto> toResponseDto(List<User> content) {
        return content.stream().map(this::toResponseDto).toList();
    }
}
