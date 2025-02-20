package com.stamp.api.store.dto.response;

import com.stamp.api.storeschedule.dto.response.ReadStoreScheduleRes;
import java.util.List;

public record ReadStoreRes(
    String businessNumber,
    String name,
    String addressCommon,
    String addressDetail,
    String businessType,
    List<ReadStoreScheduleRes> storeScheduleList) {}
