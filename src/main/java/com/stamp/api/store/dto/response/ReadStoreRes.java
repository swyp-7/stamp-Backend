package com.stamp.api.store.dto.response;

import com.stamp.api.store.entity.Store;
import com.stamp.api.storeschedule.dto.response.ReadStoreScheduleRes;
import java.util.List;

public record ReadStoreRes(
    Long id,
    String businessNumber,
    String name,
    String addressCommon,
    String addressDetail,
    String businessType,
    List<ReadStoreScheduleRes> storeScheduleList) {

  public static ReadStoreRes of(Store store) {
    return new ReadStoreRes(
        store.getId(),
        store.getBusinessNumber(),
        store.getName(),
        store.getAddressCommon(),
        store.getAddressDetail(),
        store.getBusinessType(),
        store.getStoreScheduleList().stream().map(ReadStoreScheduleRes::of).toList());
  }
}
