package com.stamp.api.auth.service;

import com.stamp.api.auth.domain.dto.SocialLoginReq;
import com.stamp.global.oauth.ProviderType;

public interface OAuthService {
    String getAuthCodeUrl(ProviderType providerType);
    String login(SocialLoginReq loginReq);
}
