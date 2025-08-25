package com.hospital_app.user_service.application.service.user;

import com.hospital_app.user_service.application.common.pagination.ApplicationPage;
import com.hospital_app.user_service.application.port.in.user.FindAllUserUseCase;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.User;

public class FindAllUserUseCaseImpl implements FindAllUserUseCase {

    private final CustomUserRepository customUserRepository;

    public FindAllUserUseCaseImpl(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public ApplicationPage<User> execute(int pageNumber, int pageSize) {
        return customUserRepository.findAll(pageNumber, pageSize);
    }
}
