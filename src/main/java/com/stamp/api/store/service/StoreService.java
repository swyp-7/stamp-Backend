package com.stamp.api.store.service;

import com.stamp.api.auth.domain.entity.EmployerUser;
import com.stamp.api.signup.domain.dto.request.CreateStoreReq;
import com.stamp.api.store.domain.entity.Store;

public interface StoreService {
  Store createStore(CreateStoreReq createStoreReq, EmployerUser employerUser);
}
