package com.drykode.maggi.client.api.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MaggiControllerAdvice {

  @ExceptionHandler(value = Throwable.class)
  public ResponseEntity<String> defaultHandler(Throwable ex) {
    log.error("Request Error {}", ex.getMessage(), ex);
    return ResponseEntity.badRequest().body("Bad Request!");
  }
}
