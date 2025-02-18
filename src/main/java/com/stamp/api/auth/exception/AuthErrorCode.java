package com.stamp.api.auth.exception;

import com.stamp.global.exception.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthErrorCode implements Error {
  EMPLOYER_USER_NOT_FOUNDED(HttpStatus.NOT_FOUND, "사장님 사용자가 존재하지 않습니다"),
  INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),
  EMAIL_ALREADY_EXISTED(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일 입니다."),
  SOCIAL_ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "소셜 로그인 사용자 등록을 위한 정보가 존재하지 않습니다.");

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
