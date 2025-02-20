package com.stamp.api.auth.service;

import com.stamp.api.auth.dto.request.LoginEmployerReq;
import com.stamp.api.auth.dto.response.LoginRes;

public interface AuthService {
  LoginRes login(LoginEmployerReq loginEmployerReq);
}
