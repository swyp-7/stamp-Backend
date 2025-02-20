package com.stamp.api.store.service;

import com.stamp.api.store.dto.request.UpdateStoreReq;

public interface UpdateStoreService {
  void updateStore(Long storeId, UpdateStoreReq updateStoreReq);
}
