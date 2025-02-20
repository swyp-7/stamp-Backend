package com.stamp.api.store.dto.request;

public record CreateStoreReq(
    String businessNumber,
    String name,
    String addressCommon,
    String addressDetail,
    String businessType) {}
