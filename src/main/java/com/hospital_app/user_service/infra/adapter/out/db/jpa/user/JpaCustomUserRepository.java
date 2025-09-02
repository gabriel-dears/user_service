package com.hospital_app.user_service.infra.adapter.out.db.jpa.user;

import com.hospital_app.common.db.pagination.ApplicationPage;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.db.UserDbOperationWrapper;
import com.hospital_app.user_service.infra.mapper.db.JpaUserMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return UserDbOperationWrapper.execute(() -> jpaUserRepository.findById(id).map(jpaUserMapper::toDomain));
    }

    @Override
    public boolean existsById(UUID id) {
        return UserDbOperationWrapper.execute(() -> jpaUserRepository.existsById(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return UserDbOperationWrapper.execute(() -> jpaUserRepository.findByUsernameAndEnabledIsTrue(username).map(jpaUserMapper::toDomain));
    }

    @Override
    public User create(User user) {
        return UserDbOperationWrapper.execute(() -> {
            var userEntity = jpaUserMapper.toEntity(user);
            var createdUserEntity = jpaUserRepository.save(userEntity);
            return jpaUserMapper.toDomain(createdUserEntity);
        });
    }

    @Override
    public boolean existsByEmail(String email) {
        return UserDbOperationWrapper.execute(() -> jpaUserRepository.existsByEmail(email));
    }

    @Override
    public boolean existsByUsername(String username) {
        return UserDbOperationWrapper.execute(() -> jpaUserRepository.existsByUsername(username));
    }

    @Override
    public ApplicationPage<User> findAll(int pageNumber, int pageSize) {
        Page<JpaUserEntity> paginatedUsers = UserDbOperationWrapper.execute(() -> jpaUserRepository.findAll((PageRequest.of(pageNumber, pageSize))));
        return new ApplicationPage<>(
                paginatedUsers.getNumber(),
                paginatedUsers.getSize(),
                paginatedUsers.getTotalPages(),
                paginatedUsers.getTotalElements(),
                paginatedUsers.isLast(),
                paginatedUsers.isFirst(),
                jpaUserMapper.toDomain(paginatedUsers.getContent()));
    }

    @Override
    public boolean existsByEmailForAnotherId(String email, UUID id) {
        return UserDbOperationWrapper.execute(() -> jpaUserRepository.existsByEmailAndIdNot(email, id));
    }

    @Override
    public boolean existsByUsernameForAnotherId(String username, UUID id) {
        return UserDbOperationWrapper.execute(() -> jpaUserRepository.existsByUsernameAndIdNot(username, id));
    }

    @Override
    public User update(User user) {
        return UserDbOperationWrapper.execute(() -> {
            var userEntity = jpaUserMapper.toEntity(user);
            var createdUserEntity = jpaUserRepository.save(userEntity);
            return jpaUserMapper.toDomain(createdUserEntity);
        });
    }

    @Override
    @Transactional
    public void changeUserStatus(UUID id, boolean enabled) {
        UserDbOperationWrapper.execute(() -> {
            jpaUserRepository.updateEnabled(id, enabled);
            return null;
        });
    }

    @Override
    @Transactional
    public void updateUserPassword(String passwordHash, UUID id) {
        UserDbOperationWrapper.execute(() -> {
            jpaUserRepository.updatePassword(passwordHash, id);
            return null;
        });
    }

    @Override
    public boolean existsByIdAndRole(UUID id, Role role) {
        return UserDbOperationWrapper.execute(() -> jpaUserRepository.existsByIdAndRoleAndEnabledIsTrue(id, role));
    }

}
