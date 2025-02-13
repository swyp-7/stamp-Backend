package com.stamp.api.signup.service;

import com.stamp.api.auth.domain.entity.EmployerUser;
import com.stamp.api.auth.service.AuthService;
import com.stamp.api.signup.domain.dto.request.SignUpReq;
import com.stamp.api.store.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {
  private final AuthService authService;
  private final StoreService storeService;

  @Transactional
  @Override
  public void signUp(SignUpReq signUpReq) {
    EmployerUser employerUser = authService.createEmployerUser(signUpReq.createEmployerUserReq());
    storeService.createStore(signUpReq.createStoreReq(), employerUser);
  }
}
