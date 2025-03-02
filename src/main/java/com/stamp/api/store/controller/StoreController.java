package com.stamp.api.store.controller;

import com.stamp.api.store.dto.request.UpdateStoreReq;
import com.stamp.api.store.dto.request.WageStatusReq;
import com.stamp.api.store.dto.response.ReadStoreRes;
import com.stamp.api.store.dto.response.WageRes;
import com.stamp.api.store.service.ReadStoreService;
import com.stamp.api.store.service.UpdateStoreService;
import com.stamp.api.store.service.WageService;
import com.stamp.global.response.ApplicationResponse;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
@RestController
public class StoreController {
  private final ReadStoreService readStoreService;
  private final UpdateStoreService updateStoreService;
  private final WageService wageService;

  @GetMapping("/storeInfo/{storeId}")
  public ApplicationResponse<ReadStoreRes> getStoreInfo(@PathVariable Long storeId) {
    return ApplicationResponse.ok(readStoreService.getStoreInfo(storeId));
  }

  @PutMapping("/{storeId}")
  public ApplicationResponse<ReadStoreRes> updateStore(
      @PathVariable Long storeId, @RequestBody UpdateStoreReq updateStoreReq) {
    return ApplicationResponse.ok(updateStoreService.updateStore(storeId, updateStoreReq));
  }

  @GetMapping("/{storeId}/employees/wage")
  public ApplicationResponse<List<WageRes>> getEmployeeWageForMonth(
      @PathVariable Long storeId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
      @AuthenticationPrincipal UserDetails userDetails) {

    return ApplicationResponse.ok(wageService.getEmployeeWage(firstDate, userDetails));
  }

  @PutMapping("/{storeId}/employees/wage/updateStatus")
  public ApplicationResponse<Void> updateEmployeeWageStatus(
      @PathVariable Long storeId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
      @RequestBody WageStatusReq wageStatusReq) {

    return ApplicationResponse.ok(wageService.updateEmplyeeWageStatus(wageStatusReq, firstDate));
  }
}
