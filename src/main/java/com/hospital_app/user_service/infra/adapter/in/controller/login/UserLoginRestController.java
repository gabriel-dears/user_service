package com.hospital_app.user_service.infra.adapter.in.controller.login;

import com.hospital_app.user_service.infra.adapter.in.controller.login.dto.UserLoginRequestDto;
import com.hospital_app.user_service.infra.swagger.UserLoginApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UserLoginRestController implements UserLoginApi {

    @PostMapping
    public void login(@RequestBody UserLoginRequestDto userLoginRequestDto) {

    }

}
