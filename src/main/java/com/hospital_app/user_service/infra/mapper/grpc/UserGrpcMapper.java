package com.hospital_app.user_service.infra.mapper.grpc;

import com.hospital_app.proto.generated.user.GetUserResponse;
import com.hospital_app.user_service.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserGrpcMapper {

    public GetUserResponse toResponseDto(User user) {
        return GetUserResponse.newBuilder()
                .setId(user.getId().toString())
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setUsername(user.getUsername())
                .setRole(user.getRole().name())
                .setEnabled(user.isEnabled())
                .build();
    }

}
