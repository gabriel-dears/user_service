package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.infra.adapter.in.controller.user.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface UserApi {

    default ResponseEntity<UserResponseDto> findById(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
