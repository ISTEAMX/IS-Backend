package com.isteamx.university.exception;

import com.isteamx.university.dto.ApiResponseWrapper;
import com.isteamx.university.monitoring.CloudWatchErrorReporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler  {

  private final CloudWatchErrorReporter errorReporter;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponseWrapper<String>> handleValidationException(MethodArgumentNotValidException ex) {
    String errors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
    return new ResponseEntity<>(ApiResponseWrapper.error(errors, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponseWrapper<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
    errorReporter.reportError("ResourceNotFound", ex);
    return new ResponseEntity<>(ApiResponseWrapper.error(ex.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiResponseWrapper<String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    return new ResponseEntity<>(ApiResponseWrapper.error(ex.getMessage(), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UserUnauthorizedException.class)
  public ResponseEntity<ApiResponseWrapper<String>> handleUserUnauthorizedException(UserUnauthorizedException ex) {
    return new ResponseEntity<>(ApiResponseWrapper.error(ex.getMessage(), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiResponseWrapper<String>> handleAccessDeniedException(AccessDeniedException ex) {
    return new ResponseEntity<>(ApiResponseWrapper.error(ex.getMessage(), HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<ApiResponseWrapper<String>> handleAlreadyExistsException(AlreadyExistsException ex) {
    errorReporter.reportError("AlreadyExists", ex);
    return new ResponseEntity<>(ApiResponseWrapper.error(ex.getMessage(), HttpStatus.CONFLICT), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseWrapper<String>> handleUnexpectedException(Exception ex) {
    log.error("Unexpected error", ex);
    errorReporter.reportError("UnexpectedException", ex);
    return new ResponseEntity<>(ApiResponseWrapper.error("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}