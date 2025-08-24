package com.hospital_app.user_service.infra.db;

import java.util.Optional;

public class JpaHelper {

    public static <E, D> Optional<D> getOptionalModelFromDb(
            OptionalEntityFromDb<E> optionalUserFromDb,
            MapFromDbToDomain<E, D> mapFromDbToDomain
    ) {
        Optional<E> jpaUserEntityOptional = optionalUserFromDb.findEntity();
        if (jpaUserEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        E jpaUserEntity = jpaUserEntityOptional.get();
        return Optional.of(mapFromDbToDomain.mapFromDbToDomain(jpaUserEntity));
    }

}
