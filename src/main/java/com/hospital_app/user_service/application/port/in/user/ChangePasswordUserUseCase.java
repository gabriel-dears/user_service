package com.hospital_app.user_service.application.port.in.user;

import com.hospital_app.user_service.application.command.UserPasswordDetails;

public interface ChangePasswordUserUseCase {
    void execute(UserPasswordDetails userPasswordDetails);
}
