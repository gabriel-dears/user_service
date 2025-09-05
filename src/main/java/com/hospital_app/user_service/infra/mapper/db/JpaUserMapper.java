package com.hospital_app.user_service.infra.mapper.db;

import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.out.db.jpa.user.JpaUserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JpaUserMapper {

    public JpaUserEntity toEntity(User user) {
        var jpaEntity = new JpaUserEntity();
        jpaEntity.setId(user.getId());
        jpaEntity.setCpf(user.getCpf());
        jpaEntity.setName(user.getName());
        jpaEntity.setUsername(user.getUsername());
        jpaEntity.setEmail(user.getEmail());
        jpaEntity.setPasswordHash(user.getPasswordHash());
        jpaEntity.setEnabled(user.isEnabled());
        jpaEntity.setRole(user.getRole());
        jpaEntity.setCreatedAt(user.getCreatedAt());
        jpaEntity.setUpdatedAt(user.getUpdatedAt());
        return jpaEntity;
    }

    public User toDomain(JpaUserEntity jpaUserEntity) {
        var domainUser = new User();
        domainUser.setId(jpaUserEntity.getId());
        domainUser.setCpf(jpaUserEntity.getCpf());
        domainUser.setName(jpaUserEntity.getName());
        domainUser.setUsername(jpaUserEntity.getUsername());
        domainUser.setEmail(jpaUserEntity.getEmail());
        domainUser.setEnabled(jpaUserEntity.isEnabled());
        domainUser.setRole(jpaUserEntity.getRole());
        domainUser.setPasswordHash(jpaUserEntity.getPasswordHash());
        domainUser.setCreatedAt(jpaUserEntity.getCreatedAt());
        domainUser.setUpdatedAt(jpaUserEntity.getUpdatedAt());
        return domainUser;
    }

    public List<User> toDomain(List<JpaUserEntity> content) {
        return content.stream().map(this::toDomain).toList();
    }
}
