package com.stamp.api.signup.dto.request;

public record CreateStoreReq(
    String businessNumber, String name, String address, String businessType) {}
