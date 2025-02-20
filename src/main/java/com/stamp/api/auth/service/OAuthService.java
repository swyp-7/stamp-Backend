package com.stamp.api.auth.service;

import com.stamp.api.auth.dto.request.SocialLoginEmployerReq;
import com.stamp.api.auth.dto.response.LoginRes;
import com.stamp.api.auth.infra.oauth.ProviderType;

public interface OAuthService {
  String getAuthCodeUrl(ProviderType providerType);

  LoginRes login(SocialLoginEmployerReq loginReq);

  LoginRes registerNewUser(SocialLoginEmployerReq loginReq);
}
