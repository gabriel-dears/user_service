package com.hospital_app.user_service.infra.adapter.out.db.jpa.user;

import com.hospital_app.user_service.application.common.pagination.ApplicationPage;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.mapper.db.JpaUserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return jpaUserHelper.getOptionalUserFromDb(() -> jpaUserRepository.findByUsernameAndEnabledIsTrue(username));
    }

    @Override
    public User create(User user) {
        var userEntity = jpaUserMapper.toEntity(user);
        var createdUserEntity = jpaUserRepository.save(userEntity);
        return jpaUserMapper.toDomain(createdUserEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }

    @Override
    public ApplicationPage<User> findAll(int pageNumber, int pageSize) {
        Page<JpaUserEntity> paginatedUsers = jpaUserRepository.findByEnabledIsTrue((PageRequest.of(pageNumber, pageSize)));
        return new ApplicationPage<>(
                paginatedUsers.getNumber(),
                paginatedUsers.getSize(),
                paginatedUsers.getTotalPages(),
                paginatedUsers.getTotalElements(),
                paginatedUsers.isLast(),
                paginatedUsers.isFirst(),
                jpaUserMapper.toDomain(paginatedUsers.getContent())
        );
    }

    @Override
    public boolean existsByEmailForAnotherId(String email, UUID id) {
        return jpaUserRepository.existsByEmailAndIdNot(email, id);
    }

    @Override
    public boolean existsByUsernameForAnotherId(String username, UUID id) {
        return jpaUserRepository.existsByUsernameAndIdNot(username, id);
    }

    @Override
    public User update(User user) {
        var userEntity = jpaUserMapper.toEntity(user);
        var createdUserEntity = jpaUserRepository.save(userEntity);
        return jpaUserMapper.toDomain(createdUserEntity);
    }

}
