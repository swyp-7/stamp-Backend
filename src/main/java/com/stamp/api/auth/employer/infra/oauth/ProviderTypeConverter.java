package com.stamp.api.auth.employer.infra.oauth;

import org.springframework.core.convert.converter.Converter;

public class ProviderTypeConverter implements Converter<String, ProviderType> {
  @Override
  public ProviderType convert(String source) {
    return ProviderType.from(source);
  }
}
