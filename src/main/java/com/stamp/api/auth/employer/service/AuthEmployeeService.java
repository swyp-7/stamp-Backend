package com.stamp.api.auth.employer.service;

import com.stamp.api.auth.employer.dto.request.LoginEmployeeReq;
import com.stamp.api.auth.employer.dto.response.EmployeeLoginRes;
import com.stamp.api.auth.employer.dto.response.LoginRes;

public interface AuthEmployeeService {
  EmployeeLoginRes login(LoginEmployeeReq req);
}
