package com.stamp.api.auth.domain.client;

import com.stamp.api.auth.domain.dto.SocialLoginReq;
import com.stamp.api.auth.domain.entity.EmployerUser;
import com.stamp.global.oauth.ProviderType;

public interface OAuthMemberClient {

    ProviderType supportType();

    EmployerUser fetch(SocialLoginReq loginReq);
}
