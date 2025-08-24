package com.hospital_app.user_service.infra.mapper.dto;

import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public User toDomain(UserRequestDto userRequestDto) {
        User user = new User();
        user.setRole(Role.valueOf(userRequestDto.role()));
        user.setUsername(userRequestDto.username());
        user.setEmail(userRequestDto.email());
        return user;
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.isEnabled()
        );
    }

}
