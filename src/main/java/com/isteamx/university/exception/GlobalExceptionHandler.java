package com.isteamx.university.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler  {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<String> handleAlreadyExistsException(AlreadyExistsException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }

}