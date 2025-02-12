package com.stamp.api.auth.domain.client;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

import com.stamp.api.auth.domain.dto.SocialLoginReq;
import com.stamp.api.auth.domain.entity.EmployerUser;
import com.stamp.global.oauth.ProviderType;
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

    public EmployerUser fetch(SocialLoginReq loginReq) {
        return getClient(loginReq.providerType()).fetch(loginReq);
    }

    private OAuthMemberClient getClient(ProviderType providerType) {
        return Optional.ofNullable(providerMap.get(providerType))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 타입입니다."));
    }
}
