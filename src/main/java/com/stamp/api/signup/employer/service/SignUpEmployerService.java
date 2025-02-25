package com.stamp.api.signup.employer.service;

import com.stamp.api.employeruser.dto.request.CreateSocialEmployerUserReq;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.signup.employer.dto.request.SignUpEmployerReq;
import com.stamp.api.store.dto.request.CreateStoreReq;

public interface SignUpEmployerService {
  void signUp(SignUpEmployerReq signUpEmployerReq);

  EmployerUser socialSignUp(
      CreateSocialEmployerUserReq createSocialEmployerUserReq,
      EmployerUser employerUser,
      CreateStoreReq createStoreReq);
}
