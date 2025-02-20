package com.stamp.api.auth.employer.infra.oauth;

public interface OAuthRequestProvider {
  ProviderType supportType();

  String provide();
}
