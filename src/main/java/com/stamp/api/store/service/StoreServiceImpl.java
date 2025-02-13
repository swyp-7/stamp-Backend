package com.stamp.api.store.service;

import com.stamp.api.auth.domain.entity.EmployerUser;
import com.stamp.api.signup.domain.dto.request.CreateStoreReq;
import com.stamp.api.store.domain.entity.Store;
import com.stamp.api.store.exception.StoreErrorCode;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
  private final StoreRepository storeRepository;

  @Override
  public Store createStore(CreateStoreReq createStoreReq, EmployerUser employerUser) {
    storeRepository
        .findByBusinessNumber(createStoreReq.businessNumber())
        .ifPresent(
            store -> {
              throw new DomainException(
                  StoreErrorCode.BUSINESS_NUMBER_ALREADY_EXISTED, "StoreServiceImpl.createStore");
            });
    return storeRepository.save(
        Store.of(
            employerUser,
            createStoreReq.businessNumber(),
            createStoreReq.name(),
            createStoreReq.address(),
            createStoreReq.businessType()));
  }
}
