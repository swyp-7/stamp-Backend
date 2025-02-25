package com.stamp.api.employeruser.service;

import com.stamp.api.auth.exception.AuthErrorCode;
import com.stamp.api.employeruser.dto.response.EmployerUserRes;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.employeruser.repository.EmployerUserRepository;
import com.stamp.api.store.dto.response.ReadStoreRes;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.exception.StoreErrorCode;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadEmployerUserServiceImpl implements ReadEmployerUserService {

  private final EmployerUserRepository employerUserRepository;
  private final StoreRepository storeRepository;

  @Override
  public EmployerUserRes getUserWithStore(Long userId) {
    EmployerUser user = findEmployerUser(userId);
    Store store = findStoreWithSchedules(user);

    return EmployerUserRes.of(user, ReadStoreRes.of(store));
  }

  private EmployerUser findEmployerUser(Long userId) {
    return employerUserRepository
        .findById(userId)
        .orElseThrow(
            () ->
                new DomainException(
                    AuthErrorCode.EMPLOYER_USER_NOT_FOUNDED,
                    "ReadEmployerUserServiceImpl.getUserWithStore"));
  }

  private Store findStoreWithSchedules(EmployerUser user) {
    return storeRepository
        .findByEmployerUserIdWithSchedules(user)
        .orElseThrow(
            () ->
                new DomainException(
                    StoreErrorCode.STORE_NOT_FOUNDED,
                    "ReadEmployerUserServiceImpl.getUserWithStore"));
  }
}
