package com.stamp.api.auth.service;

import com.stamp.api.auth.dto.request.SocialLoginReq;
import com.stamp.api.auth.dto.response.LoginRes;
import com.stamp.api.auth.entity.EmployerUser;
import com.stamp.api.auth.infra.OAuthMemberClientComposite;
import com.stamp.api.auth.repository.EmployerUserRepository;
import com.stamp.global.jwt.JwtResponse;
import com.stamp.global.jwt.util.JwtTokenProvider;
import com.stamp.global.oauth.ProviderType;
import com.stamp.global.oauth.authcode.OAuthRequestProviderComposite;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

  private final OAuthRequestProviderComposite oAuthRequestProviderComposite;
  private final OAuthMemberClientComposite oAuthMemberClientComposite;
  private final EmployerUserRepository oAuthMemberRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public String getAuthCodeUrl(ProviderType providerType) {
    return oAuthRequestProviderComposite.provide(providerType);
  }

  @Transactional
  @Override
  public LoginRes login(SocialLoginReq loginReq) {
    EmployerUser oauthMember =
        oAuthMemberClientComposite.fetch(loginReq); // OAuth 토큰 해석을 통한 사용자 정보 추출
    EmployerUser member =
        oAuthMemberRepository
            .findByOauthId(oauthMember.getOauthId()) // 토큰 해석 이후 사용자 생성 혹은 조회
            .orElseGet(() -> oAuthMemberRepository.save(oauthMember));
    JwtResponse response = jwtTokenProvider.generateToken(member);
    return LoginRes.of(response.token(), response.expiration());
  }
}
