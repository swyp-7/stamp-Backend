package com.stamp.api.signup.employer.dto.request;

import com.stamp.api.employeruser.dto.CreateEmployerUserReq;
import com.stamp.api.store.dto.request.CreateStoreReq;

public record SignUpEmployerReq(
    CreateStoreReq createStoreReq, CreateEmployerUserReq createEmployerUserReq) {}
