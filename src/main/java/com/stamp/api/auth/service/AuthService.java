package com.stamp.api.auth.service;

import com.stamp.api.auth.dto.request.LoginReq;
import com.stamp.api.auth.dto.response.LoginRes;
import com.stamp.api.employeruser.dto.CreateEmployerUserReq;
import com.stamp.api.employeruser.entity.EmployerUser;

public interface AuthService {
  EmployerUser createEmployerUser(CreateEmployerUserReq createEmployerUserReq);

  LoginRes login(LoginReq loginReq);
}
