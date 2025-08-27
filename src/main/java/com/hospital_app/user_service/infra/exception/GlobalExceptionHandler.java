package com.hospital_app.user_service.infra.exception;

import com.hospital_app.common.exception.ErrorResponseEntityFactory;
import com.hospital_app.common.exception.GlobalExceptionHandlerBase;
import com.hospital_app.common.exception.dto.ErrorResponse;
import com.hospital_app.user_service.application.exception.InvalidCredentialsException;
import com.hospital_app.user_service.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends GlobalExceptionHandlerBase {

    @ExceptionHandler(value = {
            InternalAuthenticationServiceException.class,
            InvalidCredentialsException.class
    })
    public ResponseEntity<ErrorResponse> handleInternalAuthServiceException(
            InternalAuthenticationServiceException exception,
            HttpServletRequest request) {
        Throwable cause = exception.getCause();
        if (cause instanceof InvalidCredentialsException invalidCredentialsException) {
            return ErrorResponseEntityFactory.getUnauthorized(invalidCredentialsException, request);
        }
        return ErrorResponseEntityFactory.getUnauthorized(exception, request);
    }

    @ExceptionHandler(value = {
            UserNotFoundException.class,
            RoleNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleInputNotFoundErrors(
            Exception exception,
            HttpServletRequest request) {
        return super.handleInputNotFoundErrors(exception, request);
    }

    @ExceptionHandler(value = {
            UsernameAlreadyExistsException.class,
            SamePasswordException.class,
            InvalidPasswordException.class,
            EmailAlreadyExistsException.class
    })
    public ResponseEntity<ErrorResponse> handleCustomInputErrors(
            Exception exception,
            HttpServletRequest request) {
        return super.handleCustomInputErrors(exception, request);
    }

}
