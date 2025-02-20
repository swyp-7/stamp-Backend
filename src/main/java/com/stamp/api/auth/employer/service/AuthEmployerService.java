package com.stamp.api.auth.employer.service;

import com.stamp.api.auth.employer.dto.request.LoginEmployerReq;
import com.stamp.api.auth.employer.dto.response.LoginRes;

public interface AuthEmployerService {
  LoginRes login(LoginEmployerReq loginEmployerReq);
}
