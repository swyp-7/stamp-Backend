package com.stamp.global.oauth.util;

import com.stamp.global.oauth.ProviderType;
import org.springframework.core.convert.converter.Converter;

public class ProviderTypeConverter implements Converter<String, ProviderType> {
  @Override
  public ProviderType convert(String source) {
    return ProviderType.from(source);
  }
}
