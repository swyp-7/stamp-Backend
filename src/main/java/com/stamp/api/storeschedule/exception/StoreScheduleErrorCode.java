package com.stamp.api.storeschedule.exception;

import com.stamp.global.exception.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum StoreScheduleErrorCode implements Error {
  STORE_SCHEDULE_NOT_FOUNDED(HttpStatus.NOT_FOUND, "해당 스케줄이 존재하지 않습니다."),
  STORE_SCHEDULE_ALREADY_EXISTS(HttpStatus.CONFLICT, "해당 요일의 스케줄이 이미 존재합니다.");

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
