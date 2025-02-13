package com.stamp.api.auth.service;

import com.stamp.api.auth.domain.dto.request.LoginReq;
import com.stamp.api.auth.domain.dto.response.LoginRes;
import com.stamp.api.auth.domain.entity.EmployerUser;
import com.stamp.api.signup.domain.dto.request.CreateEmployerUserReq;

public interface AuthService {
  EmployerUser createEmployerUser(CreateEmployerUserReq createEmployerUserReq);

  LoginRes login(LoginReq loginReq);
}
