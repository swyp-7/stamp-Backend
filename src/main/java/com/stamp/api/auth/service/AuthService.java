package com.stamp.api.auth.service;

import com.stamp.api.auth.dto.request.LoginReq;
import com.stamp.api.auth.dto.response.LoginRes;
import com.stamp.api.auth.entity.EmployerUser;
import com.stamp.api.signup.dto.request.CreateEmployerUserReq;

public interface AuthService {
  EmployerUser createEmployerUser(CreateEmployerUserReq createEmployerUserReq);

  LoginRes login(LoginReq loginReq);
}
