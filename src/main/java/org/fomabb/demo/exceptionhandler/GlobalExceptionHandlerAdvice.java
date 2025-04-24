package org.fomabb.demo.exceptionhandler;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import org.fomabb.demo.dto.exception.CommonExceptionResponse;
import org.fomabb.demo.exceptionhandler.exception.BusinessException;
import org.fomabb.demo.exceptionhandler.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CommonExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CommonExceptionResponse> handleValidationException(ValidationException e) {
        return ResponseEntity.unprocessableEntity()
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonExceptionResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity.unprocessableEntity()
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleRuntimeException() {
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonExceptionResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildResponseBody(e.getMessage(), e.getLocalizedMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CommonExceptionResponse> handleExpiredJwtException(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonExceptionResponse> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonExceptionResponse> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<CommonExceptionResponse> handleServletException(ServletException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    private CommonExceptionResponse buildResponseBody(String message, String exceptionClass) {
        return CommonExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .exceptionClass(exceptionClass)
                .message(message)
                .build();
    }
}
