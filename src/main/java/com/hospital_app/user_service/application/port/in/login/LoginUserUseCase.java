package com.hospital_app.user_service.application.port.in.login;

public interface LoginUserUseCase {
    String execute(String username, String password);
}
