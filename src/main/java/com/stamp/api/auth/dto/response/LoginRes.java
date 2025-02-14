package com.stamp.api.auth.dto.response;

public record LoginRes(String token, Long expirationTime) {
  public static LoginRes of(String token, Long expirationTime) {
    return new LoginRes(token, expirationTime);
  }
}
