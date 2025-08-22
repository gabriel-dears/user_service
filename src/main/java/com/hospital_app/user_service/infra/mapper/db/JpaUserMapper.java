package com.hospital_app.user_service.infra.mapper.db;

import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.adapter.out.db.jpa.user.JpaUserEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaUserMapper {

    public JpaUserEntity toEntity(User user) {
        var jpaEntity = new JpaUserEntity();
        jpaEntity.setId(user.getId());
        jpaEntity.setUsername(user.getUsername());
        jpaEntity.setPasswordHash(user.getPasswordHash());
        jpaEntity.setEnabled(user.isEnabled());
        jpaEntity.setRole(user.getRole());
        return jpaEntity;
    }

    public User toDomain(JpaUserEntity jpaUserEntity) {
        var domainUser = new User();
        domainUser.setId(jpaUserEntity.getId());
        domainUser.setUsername(jpaUserEntity.getUsername());
        domainUser.setEnabled(jpaUserEntity.isEnabled());
        domainUser.setRole(jpaUserEntity.getRole());
        domainUser.setPasswordHash(jpaUserEntity.getPasswordHash());
        return domainUser;
    }

}
