package com.stamp.api.employee.exception;

import com.stamp.global.exception.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum EmployeeErrorCode implements Error {
  EMPLOYEE_NOT_FOUNDED(HttpStatus.NOT_FOUND, "직원 정보가 존재하지 않습니다."),
  EMPLOYEE_SCHEDULE_NOT_FOUNDED(HttpStatus.NOT_FOUND, "직원 스케줄 정보가 존재하지 않습니다.");

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
