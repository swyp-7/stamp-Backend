package com.stamp.api.auth.infra.oauth;

import com.stamp.global.oauth.ProviderType;

public interface OAuthRequestProvider {
  ProviderType supportType();

  String provide();
}
