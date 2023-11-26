package com.ak.blogapp.exception;

import com.ak.blogapp.response.result.ErrorDetail;
import com.ak.blogapp.response.result.FailedBaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AssociationException.class)
    public ResponseEntity<FailedBaseResponse> handleAssociationException(
            AssociationException ex,
            HttpServletRequest httpServletRequest
    ) {
        String message = ex.getMessage();
        String path = (httpServletRequest.getQueryString() != null)
                ? httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()
                : httpServletRequest.getRequestURI();

        ErrorDetail errorDetail = new ErrorDetail(new Date(), path, message, HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailedBaseResponse.fail(Collections.singletonList(errorDetail)));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<FailedBaseResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest httpServletRequest
    ) {
        String resourceName = ex.getResourceName();
        String fieldName = ex.getFieldName();
        String fieldValue = ex.getFieldValue();
        String path = (httpServletRequest.getQueryString() != null)
                ? httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()
                : httpServletRequest.getRequestURI();

        String message = String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue);
        ErrorDetail errorDetail = new ErrorDetail(new Date(), path, message, HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FailedBaseResponse.fail(Collections.singletonList(errorDetail)));
    }

    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<FailedBaseResponse> handleInvalidEnumValueException(
            InvalidEnumValueException ex,
            HttpServletRequest httpServletRequest
    ) {
        String message = ex.getMessage();
        String path = (httpServletRequest.getQueryString() != null)
                ? httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()
                : httpServletRequest.getRequestURI();

        ErrorDetail errorDetail = new ErrorDetail(new Date(), path, message, HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailedBaseResponse.fail(Collections.singletonList(errorDetail)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailedBaseResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest httpServletRequest
    ) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String path = (httpServletRequest.getQueryString() != null)
                ? httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()
                : httpServletRequest.getRequestURI();

        List<ErrorDetail> errorDetails = fieldErrors.stream()
                .map(fieldError -> new ErrorDetail(
                        new Date(),
                        path,
                        fieldError.getDefaultMessage(),
                        HttpStatus.BAD_REQUEST.toString()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FailedBaseResponse.fail(errorDetails));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<FailedBaseResponse> handleAccessDeniedException(
            AccessDeniedException ex,
            HttpServletRequest httpServletRequest
    ) {
        String path = (httpServletRequest.getQueryString() != null)
                ? httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()
                : httpServletRequest.getRequestURI();

        String message = ex.getMessage();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), path, message, HttpStatus.FORBIDDEN.toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(FailedBaseResponse.fail(Collections.singletonList(errorDetail)));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<FailedBaseResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            HttpServletRequest httpServletRequest
    ) {
        String path = (httpServletRequest.getQueryString() != null)
                ? httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()
                : httpServletRequest.getRequestURI();

        String message = "Data integrity violation: " + Objects.requireNonNull(ex.getRootCause()).getMessage();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), path, message, HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FailedBaseResponse.fail(Collections.singletonList(errorDetail)));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<FailedBaseResponse> handleUsernameNotFoundException(
            UsernameNotFoundException ex,
            HttpServletRequest httpServletRequest
    ) {
        String path = (httpServletRequest.getQueryString() != null)
                ? httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()
                : httpServletRequest.getRequestURI();

        String message = "Username not found: " + ex.getMessage();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), path, message, HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FailedBaseResponse.fail(Collections.singletonList(errorDetail)));
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<FailedBaseResponse> handleTokenValidationException(
            TokenValidationException ex,
            HttpServletRequest httpServletRequest
    ) {
        String path = (httpServletRequest.getQueryString() != null)
                ? httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString()
                : httpServletRequest.getRequestURI();

        String message = "Token validation failed: " + ex.getMessage();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), path, message, HttpStatus.UNAUTHORIZED.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(FailedBaseResponse.fail(Collections.singletonList(errorDetail)));
    }
}
