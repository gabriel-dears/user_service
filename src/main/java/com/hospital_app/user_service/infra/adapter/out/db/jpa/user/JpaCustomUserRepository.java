package com.hospital_app.user_service.infra.adapter.out.db.jpa.user;

import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaCustomUserRepository implements CustomUserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaUserHelper jpaUserHelper;

    public JpaCustomUserRepository(JpaUserRepository jpaUserRepository, JpaUserHelper jpaUserHelper) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaUserHelper = jpaUserHelper;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserHelper.getOptionalUserFromDb(() -> jpaUserRepository.findById(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserHelper.getOptionalUserFromDb(() -> jpaUserRepository.findByUsername(username));
    }

}
