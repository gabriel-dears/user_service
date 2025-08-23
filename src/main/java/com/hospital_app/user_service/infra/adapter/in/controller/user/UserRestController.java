package com.hospital_app.user_service.infra.adapter.in.controller.user;

import com.hospital_app.user_service.application.port.in.user.FindByIdUserUseCase;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UserResponseDto;
import com.hospital_app.user_service.infra.mapper.dto.UserDtoMapper;
import com.hospital_app.user_service.infra.swagger.UserApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserRestController implements UserApi {

    private final FindByIdUserUseCase findByIdUserUseCase;
    private final UserDtoMapper userDtoMapper;

    public UserRestController(FindByIdUserUseCase findByIdUserUseCase, UserDtoMapper userDtoMapper) {
        this.findByIdUserUseCase = findByIdUserUseCase;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id) {
        User user = findByIdUserUseCase.execute(id);
        return ResponseEntity.ok(userDtoMapper.toResponseDto(user));
    }

}
