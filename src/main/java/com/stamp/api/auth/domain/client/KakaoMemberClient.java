package com.stamp.api.auth.domain.client;

import com.stamp.api.auth.domain.EmployerUser;
import com.stamp.api.auth.domain.dto.SocialLoginReq;
import com.stamp.global.oauth.ProviderType;
import com.stamp.global.oauth.authcode.domain.OAuthUserDetails;
import com.stamp.global.oauth.authcode.kakao.KakaoOAuthConfig;
import com.stamp.global.oauth.authcode.kakao.dto.KakaoMemberResponse;
import com.stamp.global.oauth.authcode.kakao.dto.KakaoToken;
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
        KakaoToken tokenInfo = kakaoApiClient.fetchToken(tokenRequestParams(loginReq.authCode()));
        KakaoMemberResponse kakaoMemberResponse =
                kakaoApiClient.fetchMember("Bearer " + tokenInfo.accessToken());
        log.info(kakaoMemberResponse.toString());
        OAuthUserDetails oAuthUser = kakaoMemberResponse.toOAuthUser();
        return EmployerUser.of(
                oAuthUser.oAuthId(),
                loginReq.name(),
                loginReq.email(),
                loginReq.contact(),
                oAuthUser.nickname(),
                oAuthUser.profileImageUrl()
        );
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
