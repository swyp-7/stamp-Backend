package com.stamp.api.store.exception;

import com.stamp.global.exception.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum StoreErrorCode implements Error {
  BUSINESS_NUMBER_ALREADY_EXISTED(HttpStatus.BAD_REQUEST, "이미 존재하는 사업자 번호 입니다."),
  STORE_NOT_FOUNDED(HttpStatus.NOT_FOUND, "존재하지 않는 가게입니다.");

  private final HttpStatus status;
  private final String message;

  @Override
  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
