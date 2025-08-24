package com.hospital_app.user_service.infra.adapter.out.db.jpa.user;

import com.hospital_app.user_service.domain.model.User;
import com.hospital_app.user_service.infra.db.JpaHelper;
import com.hospital_app.user_service.infra.db.OptionalEntityFromDb;
import com.hospital_app.user_service.infra.mapper.db.JpaUserMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaUserHelper {

    private final JpaUserMapper jpaUserMapper;

    public JpaUserHelper(JpaUserMapper jpaUserMapper) {
        this.jpaUserMapper = jpaUserMapper;
    }

    public Optional<User> getOptionalUserFromDb(OptionalEntityFromDb<JpaUserEntity> optionalUserFromDb) {
        return JpaHelper.getOptionalModelFromDb(
                optionalUserFromDb,
                jpaUserMapper::toDomain
        );
    }

}
