package com.stamp.api.signup.domain.dto.request;

public record CreateStoreReq(
    String businessNumber, String name, String address, String businessType) {}
