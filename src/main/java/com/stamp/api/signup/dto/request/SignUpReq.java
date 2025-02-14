package com.stamp.api.signup.dto.request;

import com.stamp.api.employeruser.dto.CreateEmployerUserReq;
import com.stamp.api.store.dto.request.CreateStoreReq;

public record SignUpReq(
    CreateStoreReq createStoreReq, CreateEmployerUserReq createEmployerUserReq) {}
