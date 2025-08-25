package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.application.common.pagination.ApplicationPage;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UserRequestDto;
import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface UserApi {

    default ResponseEntity<UserResponseDto> findById(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    default ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    default ResponseEntity<ApplicationPage<UserResponseDto>> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    default ResponseEntity<ApplicationPage<UserResponseDto>> update(@PathVariable UUID id, @RequestBody @Valid UserRequestDto userRequestDto) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    default ResponseEntity<Void> changeStatus(@PathVariable UUID id, @RequestParam boolean enabled) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
