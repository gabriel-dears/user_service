package com.hospital_app.user_service.application.port.in.user;

import com.hospital_app.user_service.application.common.pagination.ApplicationPage;
import com.hospital_app.user_service.domain.model.User;

public interface FindAllUserUseCase {
    ApplicationPage<User> execute(int pageNumber, int pageSize);
}
