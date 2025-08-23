package com.hospital_app.user_service.infra.adapter.out.db.jpa.user;

import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.mapper.db.JpaUserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaCustomUserRepository implements CustomUserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaUserMapper jpaUserMapper;

    public JpaCustomUserRepository(JpaUserRepository jpaUserRepository, JpaUserMapper jpaUserMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaUserMapper = jpaUserMapper;
    }

    @Override
    public Optional<User> findById(UUID id) {
        var jpaUserEntityOptional = jpaUserRepository.findById(id);
        if (jpaUserEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        JpaUserEntity jpaUserEntity = jpaUserEntityOptional.get();
        return Optional.of(jpaUserMapper.toDomain(jpaUserEntity));
    }
}
