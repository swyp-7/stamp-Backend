package com.stamp.api.employee.controller;

import com.stamp.api.attendance.dto.response.AttendanceRes;
import com.stamp.api.employee.dto.response.EmployeeAttendacneRes;
import com.stamp.api.employee.service.EmployeeService;
import com.stamp.global.response.ApplicationResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;

  /****************************
   *
   * 출/퇴근 기록 조회 API (직원용)
   *
   ****************************/

  //  직원의 한달 출/퇴근 로그 조회
  @GetMapping("/attendance/month")
  public ApplicationResponse<List<EmployeeAttendacneRes>> getAttendancesForMonth(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
      @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(employeeService.getAttendancesForMonth(firstDate, userDetails));
  }

  //  직원의 하루 출/퇴근 로그 조회
  @GetMapping("/attendance/day")
  public ApplicationResponse<List<EmployeeAttendacneRes>> getAttendancesForDayWithEmployeeId(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
      @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(employeeService.getAttendancesForDay(firstDate, userDetails));
  }
}
