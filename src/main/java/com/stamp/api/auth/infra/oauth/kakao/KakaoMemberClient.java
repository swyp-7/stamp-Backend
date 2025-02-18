package com.stamp.api.auth.infra.oauth.kakao;

import com.stamp.api.auth.dto.request.SocialLoginReq;
import com.stamp.api.auth.infra.oauth.OAuthMemberClient;
import com.stamp.api.auth.infra.oauth.OAuthUserDetails;
import com.stamp.api.auth.infra.oauth.ProviderType;
import com.stamp.api.auth.infra.oauth.kakao.dto.KakaoMemberResponse;
import com.stamp.api.employeruser.dto.CreateSocialEmployerUserReq;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.global.config.oauth.kakao.KakaoOAuthConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoMemberClient implements OAuthMemberClient {

  private final KakaoApiClient kakaoApiClient;
  private final KakaoOAuthConfig kakaoOauthConfig;

  @Override
  public ProviderType supportType() {
    return ProviderType.KAKAO;
  }

  @Override
  public EmployerUser fetch(SocialLoginReq loginReq) {
    //    KakaoToken tokenInfo = kakaoApiClient.fetchToken(tokenRequestParams(loginReq.authCode()));
    //    log.info(tokenInfo.toString());
    KakaoMemberResponse kakaoMemberResponse =
        kakaoApiClient.fetchMember("Bearer " + loginReq.accessToken());
    OAuthUserDetails oAuthUser = kakaoMemberResponse.toOAuthUser();
    CreateSocialEmployerUserReq req = loginReq.createSocialEmployerUserReq();

    return EmployerUser.of(
        oAuthUser.oAuthId(),
        req != null ? req.name() : null,
        oAuthUser.email(),
        null,
        req != null ? req.contact() : null);
  }

  private MultiValueMap<String, String> tokenRequestParams(String authCode) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", kakaoOauthConfig.clientId());
    params.add("redirect_uri", kakaoOauthConfig.redirectUri());
    params.add("code", authCode);
    params.add("client_secret", kakaoOauthConfig.clientSecret());
    return params;
  }
}
