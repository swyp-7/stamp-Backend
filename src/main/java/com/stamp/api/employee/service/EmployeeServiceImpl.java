package com.stamp.api.employee.service;

import com.stamp.api.employee.dto.response.EmployeeAttendacneRes;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeAuthorizationService employeeAuthorizationService;
  private final EmployeeAttendanceService employeeAttendanceService;

  /** Public Method 유저 타입 검사 후 직원의 한달 출/퇴근 기록 조회 */
  @Override
  public List<EmployeeAttendacneRes> getAttendancesForMonth(
      LocalDate firstDate, UserDetails userDetails) {

    // 권한 검사
    employeeAuthorizationService.isEmployee(userDetails);

    return employeeAttendanceService.getAttendancesForMonth(firstDate, userDetails);
  }

  /** Public Method 유저 타입 검사 후 직원의 일일 출/퇴근 기록 조회 */
  @Override
  public List<EmployeeAttendacneRes> getAttendancesForDay(
      LocalDate firstDate, UserDetails userDetails) {

    // 권한 검사
    employeeAuthorizationService.isEmployee(userDetails);

    return employeeAttendanceService.getAttendancesForDay(firstDate, userDetails);
  }
}
