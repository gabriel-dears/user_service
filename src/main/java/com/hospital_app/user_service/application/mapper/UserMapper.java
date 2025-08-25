package com.hospital_app.user_service.application.mapper;

import com.hospital_app.user_service.domain.model.User;

public class UserMapper {

    public static User fromInputToExistingUserForUpdate(User user, User existingUser) {
        existingUser.setName(user.getName());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        return existingUser;
    }

}
