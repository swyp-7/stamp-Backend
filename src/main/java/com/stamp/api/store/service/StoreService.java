package com.stamp.api.store.service;

import com.stamp.api.auth.entity.EmployerUser;
import com.stamp.api.signup.dto.request.CreateStoreReq;
import com.stamp.api.store.entity.Store;

public interface StoreService {
  Store createStore(CreateStoreReq createStoreReq, EmployerUser employerUser);
}
