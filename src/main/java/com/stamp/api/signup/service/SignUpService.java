package com.stamp.api.signup.service;

import com.stamp.api.signup.domain.dto.request.SignUpReq;

public interface SignUpService {
  void signUp(SignUpReq signUpReq);
}
