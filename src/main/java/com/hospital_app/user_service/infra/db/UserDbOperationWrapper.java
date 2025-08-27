package com.hospital_app.user_service.infra.db;

import com.hospital_app.common.db.DbOperationWrapper;
import com.hospital_app.user_service.application.exception.UserDbException;

public class UserDbOperationWrapper {

    public static <T> T execute(DbOperationWrapper.DbOperation<T> operation) {
        return DbOperationWrapper.execute(operation, UserDbException.class);
    }

}
