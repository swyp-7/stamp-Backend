package com.stamp.api.auth.employer.dto.response;

import com.stamp.api.employee.entity.Employee;

public record EmployeeLoginRes(
    String token, // JWT 토큰
    Long expirationTime,
    boolean isNewUser, // true시 신규 유저
    Long storeId,
    Long employeeId) {
  public static EmployeeLoginRes of(String token, Long expirationTime, Employee employee) {
    return new EmployeeLoginRes(
        token, expirationTime, false, employee.getStore().getId(), employee.getId());
  }

  public static EmployeeLoginRes newUser() {
    return new EmployeeLoginRes(null, null, true, null, null);
  }
}
