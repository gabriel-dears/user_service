package com.hospital_app.user_service.infra.adapter.out.db.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<JpaUserEntity, UUID> {
    Optional<JpaUserEntity> findByUsername(String username);

    boolean existsByEmailAndEnabledIsTrue(String email);

    boolean existsByUsernameAndEnabledIsTrue(String username);

    Optional<JpaUserEntity> findByIdAndEnabledIsTrue(UUID id);
}
