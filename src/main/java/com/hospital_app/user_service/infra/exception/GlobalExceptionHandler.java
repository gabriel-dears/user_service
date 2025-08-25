package com.hospital_app.user_service.infra.exception;

import com.hospital_app.user_service.application.exception.InvalidCredentialsException;
import com.hospital_app.user_service.domain.exception.*;
import com.hospital_app.user_service.infra.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
            InvalidCredentialsException exception,
            HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                Set.of(exception.getMessage()),
                Instant.now().toString(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(value = {
            UserNotFoundException.class,
            RoleNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleInputNotFoundErrors(
            Exception exception,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                Set.of(exception.getMessage()),
                Instant.now().toString(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
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

        ErrorResponse errorResponse = new ErrorResponse(
                Set.of(exception.getMessage()),
                Instant.now().toString(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> handleInternalAuthServiceException(
            InternalAuthenticationServiceException exception,
            HttpServletRequest request) {

        Throwable cause = exception.getCause();
        if (cause instanceof InvalidCredentialsException invalidCredentialsException) {
            ErrorResponse errorResponse = new ErrorResponse(
                    Set.of(invalidCredentialsException.getMessage()),
                    Instant.now().toString(),
                    HttpStatus.UNAUTHORIZED.value(),
                    request.getRequestURI()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        ErrorResponse errorResponse = new ErrorResponse(
                Set.of(exception.getMessage()),
                Instant.now().toString(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        Set<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toSet());

        ErrorResponse errorResponse = new ErrorResponse(
                errors,
                Instant.now().toString(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception exception,
            HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
//                "An unexpected error occurred. Please try again later or contact the administrator.",
                Set.of(exception.getMessage()),
                Instant.now().toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

}
