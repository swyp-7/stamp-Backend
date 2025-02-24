package com.stamp.api.store.service;

import com.stamp.api.store.dto.response.ReadStoreRes;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.exception.StoreErrorCode;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.api.storeschedule.dto.response.ReadStoreScheduleRes;
import com.stamp.global.exception.DomainException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadStoreServiceImpl implements ReadStoreService {

  private final StoreRepository storeRepository;

  @Override
  public ReadStoreRes getStoreInfo(Long storeId) {
    Store store = findStoreWithSchedule(storeId);

    List<ReadStoreScheduleRes> storeScheduleDtoList =
        store.getStoreScheduleList().stream().map(ReadStoreScheduleRes::toDto).toList();

    return new ReadStoreRes(
        store.getId(),
        store.getBusinessNumber(),
        store.getName(),
        store.getAddressCommon(),
        store.getAddressDetail(),
        store.getBusinessType(),
        storeScheduleDtoList);
  }

  private Store findStoreWithSchedule(Long storeId) {
    return storeRepository
        .findByIdWithSchedules(storeId)
        .orElseThrow(
            () ->
                new DomainException(
                    StoreErrorCode.STORE_NOT_FOUNDED, "UpdateStoreServiceImpl.updateStore"));
  }
}
