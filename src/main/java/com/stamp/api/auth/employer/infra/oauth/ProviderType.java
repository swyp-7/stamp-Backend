package com.stamp.api.auth.employer.infra.oauth;

import java.util.Locale;

public enum ProviderType {
  KAKAO,
  ;

  public static ProviderType from(String type) {
    return ProviderType.valueOf(type.toUpperCase(Locale.ENGLISH));
  }
}
