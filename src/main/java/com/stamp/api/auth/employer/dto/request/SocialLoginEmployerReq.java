package com.stamp.api.auth.employer.dto.request;

import com.stamp.api.auth.employer.infra.oauth.ProviderType;
import com.stamp.api.employeruser.dto.request.CreateSocialEmployerUserReq;
import com.stamp.api.store.dto.request.CreateStoreReq;
import jakarta.annotation.Nullable;

/** 소셜 로그인과 일반 로그인을 혼용함에 있어서, 소셜 로그인을 통해 가져온 정보 외에 디테일한 정보를 받아오기 위한 REQ dto 클래스 */
public record SocialLoginEmployerReq(
    ProviderType providerType,
    String accessToken,
    @Nullable CreateSocialEmployerUserReq createSocialEmployerUserReq,
    @Nullable CreateStoreReq createStoreReq) {}
