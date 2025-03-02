package com.stamp.api.extraShift.exception;

import com.stamp.global.exception.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ExtraShiftErrorCode implements Error {
  EXTRA_SHIFT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "추가 근무 요청을 생성할 수 없습니다.(해당 요일에 근무가 불가능합니다.)"),
  EXTRA_SHIFT_NOT_FOUND(HttpStatus.NOT_FOUND, "추가 근무 요청이 존재하지 않습니다."),
  NO_AUTHORITY_FOR_ACCEPT_REQUEST(HttpStatus.UNAUTHORIZED, "추가 근무 요청 변경에 대해 권한이 없습니다.");

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
