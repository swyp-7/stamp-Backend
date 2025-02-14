package com.stamp.api.auth.infra.oauth;

public interface OAuthRequestProvider {
  ProviderType supportType();

  String provide();
}
