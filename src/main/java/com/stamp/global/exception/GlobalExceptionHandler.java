package com.stamp.global.exception;

import com.stamp.global.response.ApplicationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ApplicationResponse<Error>> handleDomainException(DomainException e) {
    log.error("error : {}", e.getMessage());
    return ResponseEntity.status(e.getError().getStatus())
        .body(ApplicationResponse.error(e.getError()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApplicationResponse<Error>> handleRuntimeException(RuntimeException e) {
    log.error("error : {}", e.getMessage());
    return ResponseEntity.status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
        .body(ApplicationResponse.error(GlobalErrorCode.INTERNAL_SERVER_ERROR));
  }
}
