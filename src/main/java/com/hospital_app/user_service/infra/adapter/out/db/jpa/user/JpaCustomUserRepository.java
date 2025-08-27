package com.hospital_app.user_service.infra.adapter.out.db.jpa.user;

import com.hospital_app.user_service.application.common.pagination.ApplicationPage;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import com.hospital_app.user_service.domain.model.Role;
import com.hospital_app.user_service.domain.model.User;
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
        return jpaUserRepository.findById(id).map(jpaUserMapper::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaUserRepository.existsById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsernameAndEnabledIsTrue(username).map(jpaUserMapper::toDomain);
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
        Page<JpaUserEntity> paginatedUsers = jpaUserRepository.findAll((PageRequest.of(pageNumber, pageSize)));
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

    @Override
    @Transactional
    public void changeUserStatus(UUID id, boolean enabled) {
        jpaUserRepository.updateEnabled(id, enabled);
    }

    @Override
    @Transactional
    public void updateUserPassword(String passwordHash, UUID id) {
        jpaUserRepository.updatePassword(passwordHash, id);
    }

    @Override
    public boolean existsByIdAndRole(UUID id, Role role) {
        return jpaUserRepository.existsByIdAndRoleAndEnabledIsTrue(id, role);
    }

}
