package com.stamp.global.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
  private final Error error;

  public DomainException(Error error, String message) {
    super(message);
    this.error = error;
  }
}
