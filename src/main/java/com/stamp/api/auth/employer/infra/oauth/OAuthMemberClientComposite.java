package com.stamp.api.auth.employer.infra.oauth;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

import com.stamp.api.auth.employer.dto.request.SocialLoginEmployerReq;
import com.stamp.api.employeruser.entity.EmployerUser;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class OAuthMemberClientComposite {

  private final Map<ProviderType, OAuthMemberClient> providerMap;

  public OAuthMemberClientComposite(Set<OAuthMemberClient> clients) {
    providerMap = clients.stream().collect(toMap(OAuthMemberClient::supportType, identity()));
  }

  public EmployerUser fetch(SocialLoginEmployerReq loginReq) {
    return getClient(loginReq.providerType()).fetch(loginReq);
  }

  private OAuthMemberClient getClient(ProviderType providerType) {
    return Optional.ofNullable(providerMap.get(providerType))
        .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 타입입니다."));
  }
}
