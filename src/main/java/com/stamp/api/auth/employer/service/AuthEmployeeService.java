package com.stamp.api.auth.employer.service;

import com.stamp.api.auth.employer.dto.request.LoginEmployeeReq;
import com.stamp.api.auth.employer.dto.response.LoginRes;

public interface AuthEmployeeService {
    LoginRes login(LoginEmployeeReq req);
}
