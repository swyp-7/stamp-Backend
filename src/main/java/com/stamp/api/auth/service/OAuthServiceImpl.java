package com.stamp.api.auth.service;

import com.stamp.api.auth.domain.EmployerUser;
import com.stamp.api.auth.domain.client.OAuthMemberClientComposite;
import com.stamp.api.auth.domain.dto.SocialLoginReq;
import com.stamp.api.auth.repository.EmployerUserRepository;
import com.stamp.global.oauth.ProviderType;
import com.stamp.global.oauth.authcode.domain.OAuthRequestProviderComposite;
import com.stamp.global.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService{

    private final OAuthRequestProviderComposite oAuthRequestProviderComposite;
    private final OAuthMemberClientComposite oauthMemberClientComposite;
    private final EmployerUserRepository oauthMemberRepository;
    private final JwtProvider jwtProvider;

    @Override
    public String getAuthCodeUrl(ProviderType providerType) {
        return oAuthRequestProviderComposite.provide(providerType);
    }

    @Override
    public String login(SocialLoginReq loginReq) {
        EmployerUser oauthMember = oauthMemberClientComposite.fetch(loginReq); //OAuth 토큰 해석을 통한 사용자 정보 추출
        EmployerUser member = oauthMemberRepository.findByOauthId(oauthMember.getOauthId()) //토큰 해석 이후 사용자 생성 혹은 조회
                .orElseGet(() -> oauthMemberRepository.save(oauthMember));
        String token = jwtProvider.generateToken(member);
        log.info("member : {}", member);
        log.info("token : {}", token);
        return token;
    }

}
