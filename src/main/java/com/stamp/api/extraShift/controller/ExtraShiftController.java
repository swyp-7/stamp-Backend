package com.stamp.api.extraShift.controller;

import com.stamp.api.extraShift.dto.request.ExtraShiftReq;
import com.stamp.api.extraShift.dto.response.ExtraShiftRes;
import com.stamp.api.extraShift.service.CreateExtraShiftService;
import com.stamp.api.extraShift.service.ReadExtraShiftService;
import com.stamp.api.extraShift.service.UpdateExtraShiftService;
import com.stamp.global.response.ApplicationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/store/{storeId}/extraShifts")
@RequiredArgsConstructor
@RestController
public class ExtraShiftController {

  private final CreateExtraShiftService createExtraShiftService;
  private final ReadExtraShiftService readExtraShiftService;
  private final UpdateExtraShiftService updateExtraShiftService;

  @PostMapping("/createRequest/{employeeId}")
  public ApplicationResponse<Void> createExtraShiftRequest(
      @PathVariable("storeId") Long storeId,
      @PathVariable("employeeId") Long employeeId,
      @RequestBody ExtraShiftReq extraShiftReq) {
    createExtraShiftService.createExtraShiftRequest(storeId, employeeId, extraShiftReq);
    return ApplicationResponse.ok();
  }

  @GetMapping("/getRequests")
  public ApplicationResponse<List<ExtraShiftRes>> getExtraShiftRequests(
      @AuthenticationPrincipal UserDetails userDetails) {
    return ApplicationResponse.ok(
        readExtraShiftService.getExtraShiftRequests(Long.valueOf(userDetails.getUsername())));
  }

  @PutMapping("/{extraShiftId}/acceptRequest")
  public ApplicationResponse<ExtraShiftRes> acceptExtraShiftRequest(
      @PathVariable("extraShiftId") Long extraShiftId,
      @AuthenticationPrincipal UserDetails userDetail) {
    return ApplicationResponse.ok(
        updateExtraShiftService.acceptExtraShiftRequest(
            extraShiftId, Long.valueOf(userDetail.getUsername())));
  }

  @PutMapping("/{extraShiftId}/rejectRequest")
  public ApplicationResponse<ExtraShiftRes> rejectExtraShiftRequest(
      @PathVariable("extraShiftId") Long extraShiftId,
      @AuthenticationPrincipal UserDetails userDetail) {
    return ApplicationResponse.ok(
        updateExtraShiftService.rejectExtraShiftRequest(
            extraShiftId, Long.valueOf(userDetail.getUsername())));
  }
}
