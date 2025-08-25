package com.hospital_app.user_service.application.port.in.user;

public interface ChangePasswordUserUseCase {
    void execute(String username, String oldPassword, String newPassword);
}
