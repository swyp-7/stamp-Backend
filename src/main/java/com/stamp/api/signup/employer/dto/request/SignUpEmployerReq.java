package com.stamp.api.signup.employer.dto.request;

import com.stamp.api.employeruser.dto.request.CreateEmployerUserReq;
import com.stamp.api.store.dto.request.CreateStoreReq;

public record SignUpEmployerReq(
    CreateEmployerUserReq createEmployerUserReq, CreateStoreReq createStoreReq) {}
