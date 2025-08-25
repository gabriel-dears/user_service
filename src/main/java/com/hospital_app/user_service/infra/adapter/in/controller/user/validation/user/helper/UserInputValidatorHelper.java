package com.hospital_app.user_service.infra.adapter.in.controller.user.validation.user.helper;

import java.util.UUID;

public class UserInputValidatorHelper {

    public static boolean isCreateUserFlow(UUID id) {
        return id == null;
    }

    public static boolean isUpdateUserFlow(UUID id) {
        return id != null;
    }

}
