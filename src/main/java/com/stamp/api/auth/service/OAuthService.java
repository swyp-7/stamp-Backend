package com.stamp.api.auth.service;

import com.stamp.api.auth.dto.request.SocialLoginReq;
import com.stamp.api.auth.dto.response.LoginRes;
import com.stamp.global.oauth.ProviderType;

public interface OAuthService {
  String getAuthCodeUrl(ProviderType providerType);

  LoginRes login(SocialLoginReq loginReq);
}
