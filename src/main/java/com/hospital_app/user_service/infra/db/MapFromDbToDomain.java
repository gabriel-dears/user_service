package com.hospital_app.user_service.infra.db;

@FunctionalInterface
public interface MapFromDbToDomain<E, D> {
    D mapFromDbToDomain(E entity);
}
