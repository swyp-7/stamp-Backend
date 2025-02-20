package com.stamp.api.store.service;

import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.store.dto.request.CreateStoreReq;
import com.stamp.api.store.entity.Store;

public interface CreateStoreService {
  Store createStore(CreateStoreReq createStoreReq, EmployerUser employerUser);
}
