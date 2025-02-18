package com.stamp.api.signup.service;

import com.stamp.api.employeruser.dto.CreateSocialEmployerUserReq;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.signup.dto.request.SignUpReq;
import com.stamp.api.store.dto.request.CreateStoreReq;

public interface SignUpService {
  void signUp(SignUpReq signUpReq);

  EmployerUser socialSignUp(
      CreateSocialEmployerUserReq createSocialEmployerUserReq,
      EmployerUser employerUser,
      CreateStoreReq createStoreReq);
}
