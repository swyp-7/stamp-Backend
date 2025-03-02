package com.stamp.api.employee.service;

import com.stamp.api.employee.dto.response.EmployeeAttendacneRes;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

public interface EmployeeAttendanceService {

  /******************************
   * 직원용 출/퇴근 기록 조회 클래스
   * 사용시 검증 로직과 함께 사용 권장
   ******************************/
  List<EmployeeAttendacneRes> getAttendancesForMonth(LocalDate firstDate, UserDetails userDetails);

  List<EmployeeAttendacneRes> getAttendancesForDay(LocalDate firstDate, UserDetails userDetails);
}
