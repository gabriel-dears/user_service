package com.hospital_app.user_service.infra.adapter.out.db.jpa.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<JpaUserEntity, UUID> {

    Optional<JpaUserEntity> findByUsernameAndEnabledIsTrue(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<JpaUserEntity> findByIdAndEnabledIsTrue(UUID id);

    Page<JpaUserEntity> findByEnabledIsTrue(Pageable pageable);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByUsernameAndIdNot(String username, UUID id);


}
