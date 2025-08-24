package com.hospital_app.user_service.infra.db;

import java.util.Optional;

@FunctionalInterface
public interface OptionalEntityFromDb<E> {
    Optional<E> findEntity();
}
