package com.stamp.api.signup.employer.service;

import com.stamp.api.employeruser.dto.CreateSocialEmployerUserReq;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.employeruser.service.CreateEmployerUserService;
import com.stamp.api.signup.employer.dto.request.SignUpEmployerReq;
import com.stamp.api.store.dto.request.CreateStoreReq;
import com.stamp.api.store.service.CreateStoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignUpEmployerServiceImpl implements SignUpEmployerService {
  private final CreateEmployerUserService createEmployerUserService;
  private final CreateStoreService createStoreService;

  @Transactional
  @Override
  public void signUp(SignUpEmployerReq signUpEmployerReq) {
    EmployerUser employerUser =
        createEmployerUserService.createEmployerUser(signUpEmployerReq.createEmployerUserReq());
    createStoreService.createStore(signUpEmployerReq.createStoreReq(), employerUser);
  }

  @Override
  public EmployerUser socialSignUp(
      CreateSocialEmployerUserReq createSocialEmployerUserReq,
      EmployerUser oauthEmployerUser,
      CreateStoreReq createStoreReq) {
    return createEmployerUserService.createSocialEmployerUser(
        createSocialEmployerUserReq, oauthEmployerUser);
  }
}
