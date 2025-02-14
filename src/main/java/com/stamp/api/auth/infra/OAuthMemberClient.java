package com.stamp.api.auth.infra;

import com.stamp.api.auth.dto.request.SocialLoginReq;
import com.stamp.api.auth.entity.EmployerUser;
import com.stamp.global.oauth.ProviderType;

public interface OAuthMemberClient {

  ProviderType supportType();

  EmployerUser fetch(SocialLoginReq loginReq);
}
