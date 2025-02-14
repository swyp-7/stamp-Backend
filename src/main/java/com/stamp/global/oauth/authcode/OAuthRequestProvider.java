package com.stamp.global.oauth.authcode;

import com.stamp.global.oauth.ProviderType;

public interface OAuthRequestProvider {
  ProviderType supportType();

  String provide();
}
