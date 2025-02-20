package com.stamp.api.auth.employer.entity;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.stamp.api.auth.employer.infra.oauth.ProviderType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class OAuthId {

  @Column(name = "oauth_server_id")
  private String oauthServerId;

  @Enumerated(STRING)
  @Column(name = "oauth_server_provider")
  private ProviderType providerType;

  public String oauthServerId() {
    return oauthServerId;
  }

  public ProviderType oauthServer() {
    return providerType;
  }
}
