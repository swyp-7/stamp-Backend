package com.stamp.api.auth.employer.service;

import com.stamp.api.auth.employer.dto.request.SocialLoginEmployerReq;
import com.stamp.api.auth.employer.dto.response.LoginRes;
import com.stamp.api.auth.employer.infra.oauth.OAuthMemberClientComposite;
import com.stamp.api.auth.employer.infra.oauth.OAuthRequestProviderComposite;
import com.stamp.api.auth.employer.infra.oauth.ProviderType;
import com.stamp.api.auth.exception.AuthErrorCode;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.employeruser.repository.EmployerUserRepository;
import com.stamp.api.signup.employer.service.SignUpEmployerService;
import com.stamp.global.exception.DomainException;
import com.stamp.global.jwt.JwtResponse;
import com.stamp.global.jwt.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthEmployerServiceImpl implements OAuthEmployerService {

  private final OAuthRequestProviderComposite oAuthRequestProviderComposite;
  private final OAuthMemberClientComposite oAuthMemberClientComposite;
  private final EmployerUserRepository employerUserRepository;
  private final SignUpEmployerService signUpEmployerService;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public String getAuthCodeUrl(ProviderType providerType) {
    return oAuthRequestProviderComposite.provide(providerType);
  }

  @Transactional(readOnly = true)
  @Override
  public LoginRes login(SocialLoginEmployerReq loginReq) {
    EmployerUser oauthEmployerUser = oAuthMemberClientComposite.fetch(loginReq);

    return employerUserRepository
        .findByOauthId(oauthEmployerUser.getOauthId())
        .map(this::createLoginResponse)
        .orElse(LoginRes.newUser());
    //        .orElseGet(() -> registerNewUser(loginReq, oauthEmployerUser));
  }

  @Transactional
  @Override
  public LoginRes registerNewUser(SocialLoginEmployerReq loginReq) {
    EmployerUser oauthEmployerUser = oAuthMemberClientComposite.fetch(loginReq);
    if (loginReq.createSocialEmployerUserReq() == null || loginReq.createStoreReq() == null) {
      throw new DomainException(
          AuthErrorCode.SOCIAL_ILLEGAL_ARGUMENT, "OAuthServiceImpl.registerNewUser");
    }
    EmployerUser employerUser =
        signUpEmployerService.socialSignUp(
            loginReq.createSocialEmployerUserReq(), oauthEmployerUser, loginReq.createStoreReq());
    return createLoginResponse(employerUser);
  }

  private LoginRes createLoginResponse(EmployerUser employerUser) {
    JwtResponse response = jwtTokenProvider.generateToken(employerUser);
    return LoginRes.of(response.token(), response.expiration());
  }
}
