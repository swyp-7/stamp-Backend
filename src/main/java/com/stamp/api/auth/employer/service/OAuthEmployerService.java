package com.stamp.api.auth.employer.service;

import com.stamp.api.auth.employer.dto.request.SocialLoginEmployerReq;
import com.stamp.api.auth.employer.dto.response.LoginRes;
import com.stamp.api.auth.employer.infra.oauth.ProviderType;

public interface OAuthEmployerService {
  String getAuthCodeUrl(ProviderType providerType);

  LoginRes login(SocialLoginEmployerReq loginReq);

  LoginRes registerNewUser(SocialLoginEmployerReq loginReq);
}
