package com.stamp.api.auth.infra.oauth;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class OAuthRequestProviderComposite {

  private final Map<ProviderType, OAuthRequestProvider> providerMap;

  public OAuthRequestProviderComposite(Set<OAuthRequestProvider> providers) {
    providerMap = providers.stream().collect(toMap(OAuthRequestProvider::supportType, identity()));
  }

  public String provide(ProviderType providerType) {
    return getProvider(providerType).provide();
  }

  private OAuthRequestProvider getProvider(ProviderType providerType) {
    return Optional.ofNullable(providerMap.get(providerType))
        .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 타입입니다."));
  }
}
