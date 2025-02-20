package com.stamp.api.store.dto.request;

import com.stamp.api.storeschedule.dto.request.UpdateStoreScheduleReq;
import java.util.List;

public record UpdateStoreReq(
    String businessNumber,
    String name,
    String addressCommon,
    String addressDetail,
    String businessType,
    List<UpdateStoreScheduleReq> storeScheduleList) {}
