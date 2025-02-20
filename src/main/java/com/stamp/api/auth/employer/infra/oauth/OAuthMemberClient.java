package com.stamp.api.auth.employer.infra.oauth;

import com.stamp.api.auth.employer.dto.request.SocialLoginEmployerReq;
import com.stamp.api.employeruser.entity.EmployerUser;

public interface OAuthMemberClient {

  ProviderType supportType();

  EmployerUser fetch(SocialLoginEmployerReq loginReq);
}
