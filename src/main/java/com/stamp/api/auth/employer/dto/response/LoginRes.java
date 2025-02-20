package com.stamp.api.auth.employer.dto.response;

public record LoginRes(String token, Long expirationTime, boolean isNewUser) {
  public static LoginRes of(String token, Long expirationTime) {
    return new LoginRes(token, expirationTime, false);
  }

  public static LoginRes newUser() {
    return new LoginRes(null, null, true);
  }
}
