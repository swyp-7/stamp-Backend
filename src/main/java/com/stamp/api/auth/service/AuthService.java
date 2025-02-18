package com.stamp.api.auth.service;

import com.stamp.api.auth.dto.request.LoginReq;
import com.stamp.api.auth.dto.response.LoginRes;

public interface AuthService {
  LoginRes login(LoginReq loginReq);
}
