package com.hospital_app.user_service.infra.swagger;

import com.hospital_app.user_service.infra.adapter.in.controller.login.dto.UserLoginRequestDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserLoginApi {

    default void login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
