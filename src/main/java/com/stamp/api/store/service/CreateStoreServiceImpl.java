package com.stamp.api.store.service;

import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.store.dto.request.CreateStoreReq;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.exception.StoreErrorCode;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.global.exception.DomainException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateStoreServiceImpl implements CreateStoreService {
  private final StoreRepository storeRepository;

  @Transactional
  @Override
  public Store createStore(CreateStoreReq createStoreReq, EmployerUser employerUser) {
    storeRepository
        .findByBusinessNumber(createStoreReq.businessNumber())
        .ifPresent(
            store -> {
              throw new DomainException(
                  StoreErrorCode.BUSINESS_NUMBER_ALREADY_EXISTED, "StoreServiceImpl.createStore");
            });
    return storeRepository.save(Store.of(createStoreReq, employerUser));
  }
}
