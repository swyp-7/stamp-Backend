package com.stamp.api.attendance.controller;

import com.stamp.api.attendance.dto.response.QRCodeRes;
import com.stamp.api.attendance.service.LocalAttendanceService;
import com.stamp.global.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AttendanceControllerLocal {

  private final LocalAttendanceService attendanceService;

  // QR코드 생성 API
  @GetMapping("/store/{storeId}/employees/createQR/local")
  public ApplicationResponse<QRCodeRes> createQR(
      @PathVariable("storeId") String storeId, @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(attendanceService.createQR(Long.valueOf(storeId), userDetails));
  }

  //  QR코드 조회 API
  @GetMapping("/store/{storeId}/employees/getQR/local")
  public ApplicationResponse<QRCodeRes> getQR(
      @PathVariable("storeId") String storeId, @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(attendanceService.getQR(Long.valueOf(storeId), userDetails));
  }
}
