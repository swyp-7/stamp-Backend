package com.stamp.api.attendance.controller;

import com.stamp.api.attendance.dto.requeset.AttendanceReq;
import com.stamp.api.attendance.dto.response.QRCodeRes;
import com.stamp.api.attendance.exception.QRCodeErrorCode;
import com.stamp.api.attendance.service.AttendanceService;
import com.stamp.api.attendance.service.QRManageService;
import com.stamp.api.employee.entity.Employee;
import com.stamp.global.exception.DomainException;
import com.stamp.global.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ApplicationResponse<QRCodeRes> createQR(@PathVariable("storeId") String storeId,
                                                   @AuthenticationPrincipal UserDetails userDetails) {

        return ApplicationResponse.ok(attendanceService.createQR(Long.valueOf(storeId), userDetails));
    }

    //  QR코드 조회 API
    @GetMapping("/store/{storeId}/employees/getQR")
    public ApplicationResponse<QRCodeRes> getQR(@PathVariable("storeId") String storeId,
                                                @AuthenticationPrincipal UserDetails userDetails) {

        return ApplicationResponse.ok(attendanceService.getQR(Long.valueOf(storeId), userDetails));
    }

    //  출근 로그 생성 API
    @PostMapping("/store/{storeId}/employees/punchIn")
    public ApplicationResponse<Void> punchIn(@PathVariable("storeId") String storeId,
                                             @RequestBody AttendanceReq attendanceReq,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        attendanceService.punchIn(Long.valueOf(storeId), userDetails, attendanceReq.authCode());
        return ApplicationResponse.ok();
    }

    //  퇴근 로그 생성 API
    @PostMapping("/store/{storeId}/employees/punchOut")
    public ApplicationResponse<Void> punchOut(@PathVariable("storeId") String storeId,
                                             @RequestBody AttendanceReq attendanceReq,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        attendanceService.punchOut(Long.valueOf(storeId), userDetails, attendanceReq.authCode());
        return ApplicationResponse.ok();
    }
}
