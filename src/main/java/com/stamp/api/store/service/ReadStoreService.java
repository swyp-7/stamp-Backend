package com.stamp.api.store.service;

import com.stamp.api.store.dto.response.ReadStoreRes;

public interface ReadStoreService {
  ReadStoreRes getStoreInfo(Long storeId);
}
