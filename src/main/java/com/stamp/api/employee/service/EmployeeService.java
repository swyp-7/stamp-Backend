package com.stamp.api.employee.service;

import com.stamp.api.employee.dto.response.EmployeeAttendacneRes;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

public interface EmployeeService {

  /**********************************************
   * 직원 도메인에서 요구되는 전반적인 서비스 래핑 클래스
   * 구현시 검증 로직 추가 필수
   **********************************************/
  List<EmployeeAttendacneRes> getAttendancesForMonth(LocalDate firstDate, UserDetails userDetails);

  List<EmployeeAttendacneRes> getAttendancesForDay(LocalDate firstDate, UserDetails userDetails);
}
