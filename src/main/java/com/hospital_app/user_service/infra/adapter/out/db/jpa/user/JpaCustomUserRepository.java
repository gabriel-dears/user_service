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
    private final JpaUserHelper jpaUserHelper;
    private final JpaUserMapper jpaUserMapper;

    public JpaCustomUserRepository(JpaUserRepository jpaUserRepository, JpaUserHelper jpaUserHelper, JpaUserMapper jpaUserMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaUserHelper = jpaUserHelper;
        this.jpaUserMapper = jpaUserMapper;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserHelper.getOptionalUserFromDb(() -> jpaUserRepository.findByIdAndEnabledIsTrue(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserHelper.getOptionalUserFromDb(() -> jpaUserRepository.findByUsername(username));
    }

    @Override
    public User create(User user) {
        var userEntity = jpaUserMapper.toEntity(user);
        var createdUserEntity = jpaUserRepository.save(userEntity);
        return jpaUserMapper.toDomain(createdUserEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmailAndEnabledIsTrue(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaUserRepository.existsByUsernameAndEnabledIsTrue(username);
    }

}
