package com.stamp.api.employee.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface EmployeeAuthorizationService {

  /*******************************
   * Employee 도메인 관련 검증 클래스
   * 상황별 검증 메서드 제공
   ******************************/

  void isEmployee(UserDetails user);

  void checkEmployeeStoreAuthority(Long storeId, UserDetails userDetails);
}
