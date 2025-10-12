package com.smessenger.api.config;

import com.smessenger.api.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import com.smessenger.api.exception.AuthenticationFailedException;
import com.smessenger.api.exception.CustomException;
import com.smessenger.api.exception.UsernameAlreadyExistsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
      org.springframework.http.HttpStatusCode status, WebRequest request) {
    return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse("INVALID_JSON", "Malformed JSON request"));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      org.springframework.http.HttpStatusCode status, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
    // include a short summary and let clients call validation endpoints for details
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse("VALIDATION_FAILED", "Validation failed"));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("ACCESS_DENIED", "Access denied"));
  }

  @ExceptionHandler(AuthenticationFailedException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationFailed(AuthenticationFailedException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        new ErrorResponse("AUTH_FAILED", ex.getMessage() == null ? "Invalid username or password" : ex.getMessage()));
  }

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserExists(UsernameAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(
        new ErrorResponse("USERNAME_TAKEN", ex.getMessage() == null ? "Username is already taken" : ex.getMessage()));
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        new ErrorResponse("CUSTOM_SERVER_ERROR", ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
    // Log exception here if needed
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse("INTERNAL_ERROR", "Internal server error"));
  }
}
