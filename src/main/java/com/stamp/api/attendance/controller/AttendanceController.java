package com.stamp.api.attendance.controller;

import com.stamp.api.attendance.dto.requeset.AttendanceReq;
import com.stamp.api.attendance.dto.response.AttendanceRes;
import com.stamp.api.attendance.dto.response.QRCodeRes;
import com.stamp.api.attendance.service.AttendanceService;
import com.stamp.global.response.ApplicationResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AttendanceController {

  private final AttendanceService attendanceService;

  // QR코드 생성 API
  @GetMapping("/store/{storeId}/employees/createQR")
  public ApplicationResponse<QRCodeRes> createQR(
      @PathVariable("storeId") String storeId, @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(attendanceService.createQR(Long.valueOf(storeId), userDetails));
  }

  //  QR코드 조회 API
  @GetMapping("/store/{storeId}/employees/getQR")
  public ApplicationResponse<QRCodeRes> getQR(
      @PathVariable("storeId") String storeId, @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(attendanceService.getQR(Long.valueOf(storeId), userDetails));
  }

  //  출근 로그 생성 API
  @PostMapping("/store/{storeId}/employees/punchIn")
  public ApplicationResponse<Void> punchIn(
      @PathVariable("storeId") String storeId,
      @RequestBody AttendanceReq attendanceReq,
      @AuthenticationPrincipal UserDetails userDetails) {

    attendanceService.punchIn(Long.valueOf(storeId), userDetails, attendanceReq.authCode());
    return ApplicationResponse.ok();
  }

  //  퇴근 로그 생성 API
  @PostMapping("/store/{storeId}/employees/punchOut")
  public ApplicationResponse<Void> punchOut(
      @PathVariable("storeId") String storeId,
      @RequestBody AttendanceReq attendanceReq,
      @AuthenticationPrincipal UserDetails userDetails) {

    attendanceService.punchOut(Long.valueOf(storeId), userDetails, attendanceReq.authCode());
    return ApplicationResponse.ok();
  }

  //  가게 직원들의 한달 출/퇴근 로그 조회
  @GetMapping("/store/{storeId}/employees/attendance/month/all")
  public ApplicationResponse<List<AttendanceRes>> getAttendancesForMonth(
      @PathVariable("storeId") String storeId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
      @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(
        attendanceService.getAttendancesForMonth(Long.valueOf(storeId), firstDate, userDetails));
  }

  //  가게 직원들의 하루 출/퇴근 로그 조회
  @GetMapping("/store/{storeId}/employees/attendance/day/all")
  public ApplicationResponse<List<AttendanceRes>> getAttendancesForDay(
      @PathVariable("storeId") String storeId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
      @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(
        attendanceService.getAttendancesForDay(Long.valueOf(storeId), firstDate, userDetails));
  }

  //  특정 직원의 한달 출/퇴근 로그 조회
  @GetMapping("/store/{storeId}/employees/attendance/month/{employeeId}")
  public ApplicationResponse<List<AttendanceRes>> getAttendancesForMonthWithEmployeeId(
      @PathVariable("storeId") String storeId,
      @PathVariable("employeeId") String employeeId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
      @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(
        attendanceService.getAttendancesForMonthWithEmployeeId(
            Long.valueOf(storeId), firstDate, Long.valueOf(employeeId), userDetails));
  }

  //  특정 직원의 하루 출/퇴근 로그 조회
  @GetMapping("/store/{storeId}/employees/attendance/day/{employeeId}")
  public ApplicationResponse<List<AttendanceRes>> getAttendancesForDayWithEmployeeId(
      @PathVariable("storeId") String storeId,
      @PathVariable("employeeId") String employeeId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
      @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(
        attendanceService.getAttendancesForDayWithEmployeeId(
            Long.valueOf(storeId), firstDate, Long.valueOf(employeeId), userDetails));
  }
}
