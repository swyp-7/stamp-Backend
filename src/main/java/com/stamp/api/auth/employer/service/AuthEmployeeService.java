package com.stamp.api.auth.employer.service;

import com.stamp.api.auth.employer.dto.request.LoginEmployeeReq;
import com.stamp.api.auth.employer.dto.response.EmployeeLoginRes;

public interface AuthEmployeeService {
  EmployeeLoginRes login(LoginEmployeeReq req);
}
