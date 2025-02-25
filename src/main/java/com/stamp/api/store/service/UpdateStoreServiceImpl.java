package com.stamp.api.store.service;

import com.stamp.api.store.dto.request.UpdateStoreReq;
import com.stamp.api.store.dto.response.ReadStoreRes;
import com.stamp.api.store.entity.Store;
import com.stamp.api.store.exception.StoreErrorCode;
import com.stamp.api.store.repository.StoreRepository;
import com.stamp.api.storeschedule.dto.request.UpdateStoreScheduleReq;
import com.stamp.api.storeschedule.entity.StoreSchedule;
import com.stamp.api.storeschedule.exception.StoreScheduleErrorCode;
import com.stamp.api.storeschedule.repository.StoreScheduleRepository;
import com.stamp.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateStoreServiceImpl implements UpdateStoreService {
  private final StoreRepository storeRepository;
  private final StoreScheduleRepository storeScheduleRepository;

  @Transactional
  @Override
  public ReadStoreRes updateStore(Long storeId, UpdateStoreReq updateStoreReq) {
    Store store = findStoreWithSchedule(storeId);
    store.update(updateStoreReq);

    for (UpdateStoreScheduleReq storeScheduleReq : updateStoreReq.storeScheduleList()) {
      if (storeScheduleReq.id() == null) {
        checkScheduleExists(store, storeScheduleReq);
        StoreSchedule storeSchedule = StoreSchedule.of(storeScheduleReq, store);
        storeScheduleRepository.save(storeSchedule);
      } else {
        StoreSchedule existingSchedule = findStoreSchedule(storeScheduleReq.id());
        existingSchedule.update(storeScheduleReq);
      }
    }

    return ReadStoreRes.of(store);
  }

  private Store findStoreWithSchedule(Long storeId) {
    return storeRepository
        .findByIdWithSchedules(storeId)
        .orElseThrow(
            () ->
                new DomainException(
                    StoreErrorCode.STORE_NOT_FOUNDED, "UpdateStoreServiceImpl.updateStore"));
  }

  private StoreSchedule findStoreSchedule(Long storeScheduleId) {
    return storeScheduleRepository
        .findById(storeScheduleId)
        .orElseThrow(
            () ->
                new DomainException(
                    StoreScheduleErrorCode.STORE_SCHEDULE_NOT_FOUNDED,
                    "UpdateStoreServiceImpl.updateStore"));
  }

  private void checkScheduleExists(Store store, UpdateStoreScheduleReq storeScheduleReq) {
    boolean scheduleExists =
        store.getStoreScheduleList().stream()
            .anyMatch(
                existingSchedule -> existingSchedule.getWeekDay() == storeScheduleReq.weekDay());
    if (scheduleExists) {
      throw new DomainException(
          StoreScheduleErrorCode.STORE_SCHEDULE_ALREADY_EXISTS,
          "UpdateStoreServiceImpl.checkScheduleExists");
    }
  }
}
