package com.hospital_app.user_service.infra.adapter.in.controller.user;

import com.hospital_app.user_service.application.port.in.pagination.ApplicationPage;
import com.hospital_app.user_service.application.port.in.user.CreateUserUseCase;
import com.hospital_app.user_service.application.port.in.user.FindAllUserUseCase;
import com.hospital_app.user_service.application.port.in.user.FindByIdUserUseCase;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UserRequestDto;
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
    private final UserDtoMapper userDtoMapper;

    public UserRestController(FindByIdUserUseCase findByIdUserUseCase, CreateUserUseCase createUserUseCase, FindAllUserUseCase findAllUserUseCase, UserDtoMapper userDtoMapper) {
        this.findByIdUserUseCase = findByIdUserUseCase;
        this.createUserUseCase = createUserUseCase;
        this.findAllUserUseCase = findAllUserUseCase;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id) {
        User user = findByIdUserUseCase.execute(id);
        return ResponseEntity.ok(userDtoMapper.toResponseDto(user));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        User domain = userDtoMapper.toDomain(userRequestDto);
        User createdUser = createUserUseCase.execute(domain);
        return ResponseEntity.ok(userDtoMapper.toResponseDto(createdUser));
    }

    @GetMapping
    public ResponseEntity<ApplicationPage<UserResponseDto>> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(userDtoMapper.toResponseDto(findAllUserUseCase.execute(pageNumber, pageSize)));
    }

}
