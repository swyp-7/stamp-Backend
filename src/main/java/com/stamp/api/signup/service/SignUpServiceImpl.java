package com.stamp.api.signup.service;

import com.stamp.api.employeruser.dto.CreateSocialEmployerUserReq;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.employeruser.service.CreateEmployerUserService;
import com.stamp.api.signup.dto.request.SignUpReq;
import com.stamp.api.store.dto.request.CreateStoreReq;
import com.stamp.api.store.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {
  private final CreateEmployerUserService createEmployerUserService;
  private final StoreService storeService;

  @Transactional
  @Override
  public void signUp(SignUpReq signUpReq) {
    EmployerUser employerUser =
        createEmployerUserService.createEmployerUser(signUpReq.createEmployerUserReq());
    storeService.createStore(signUpReq.createStoreReq(), employerUser);
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
