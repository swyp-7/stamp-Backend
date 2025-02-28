package com.stamp.api.auth.employer.dto.response;

public record EmployeeLoginRes(String token, Long expirationTime, boolean isNewUser, Long storeId) {
  public static EmployeeLoginRes of(String token, Long expirationTime, Long storeId) {
    return new EmployeeLoginRes(token, expirationTime, false, storeId);
  }

  public static EmployeeLoginRes newUser() {
    return new EmployeeLoginRes(null, null, true, null);
  }
}
