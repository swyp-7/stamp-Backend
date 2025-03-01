package com.stamp.api.attendance.exception;

import com.stamp.global.exception.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AttendanceErrorCode implements Error {
  AUTH_CODE_FAIL_ERROR(HttpStatus.BAD_REQUEST, "인증 코드 오류"),
  DUPLICATE_PUNCH_IN_ERROR(HttpStatus.CONFLICT, "중복 출근 오류"),
  DUPLICATE_PUNCH_OUT_ERROR(HttpStatus.CONFLICT, "중복 퇴근 오류"),
  NO_AUTHORITY_FOR_STORE_ERROR(HttpStatus.UNAUTHORIZED, "가게에 대한 엑세스 권한 없음."),
  NO_AUTHORITY_FOR_EMPLOYEE_ERROR(HttpStatus.UNAUTHORIZED, "해당 직원에 대한 엑세스 권한 없음."),
  EMPLOYEE_ID_ERROR(HttpStatus.BAD_REQUEST, "employeeId에 해당하는 유저가 존재하지 않습니다."),
  NO_STORE_ERROR(HttpStatus.BAD_REQUEST, "해당 ID의 가게가 존재하지 않습니다.");

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
