package com.stamp.api.store.service;

import com.stamp.api.store.dto.request.UpdateStoreReq;
import com.stamp.api.store.dto.response.ReadStoreRes;

public interface UpdateStoreService {
  ReadStoreRes updateStore(Long storeId, UpdateStoreReq updateStoreReq);
}
