package com.stamp.api.store.controller;

import com.stamp.api.store.dto.request.UpdateStoreReq;
import com.stamp.api.store.dto.response.ReadStoreRes;
import com.stamp.api.store.service.ReadStoreService;
import com.stamp.api.store.service.UpdateStoreService;
import com.stamp.global.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
@RestController
public class StoreController {
  private final ReadStoreService readStoreService;
  private final UpdateStoreService updateStoreService;

  @GetMapping("/storeInfo/{storeId}")
  public ApplicationResponse<ReadStoreRes> getStoreInfo(@PathVariable Long storeId) {
    return ApplicationResponse.ok(readStoreService.getStoreInfo(storeId));
  }

  @PutMapping("/{storeId}")
  public ApplicationResponse<Void> updateStore(
      @PathVariable Long storeId, @RequestBody UpdateStoreReq updateStoreReq) {
    updateStoreService.updateStore(storeId, updateStoreReq);
    return ApplicationResponse.ok();
  }
}
