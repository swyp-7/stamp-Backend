package com.stamp.api.auth.infra.oauth.kakao;

import com.stamp.api.auth.infra.oauth.OAuthRequestProvider;
import com.stamp.api.auth.infra.oauth.ProviderType;
import com.stamp.global.config.oauth.kakao.KakaoOAuthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoOAuthRequestProvider implements OAuthRequestProvider {

  private final KakaoOAuthConfig kakaoOAuthConfig;

  @Override
  public ProviderType supportType() {
    return ProviderType.KAKAO;
  }

  @Override
  public String provide() {
    return UriComponentsBuilder.fromUriString("https://kauth.kakao.com/oauth/authorize")
        .queryParam("response_type", "code")
        .queryParam("client_id", kakaoOAuthConfig.clientId())
        .queryParam("redirect_uri", kakaoOAuthConfig.redirectUri())
        .queryParam("scope", String.join(",", kakaoOAuthConfig.scope()))
        .toUriString();
  }
}
