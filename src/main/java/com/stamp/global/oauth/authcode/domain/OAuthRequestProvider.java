package com.stamp.global.oauth.authcode.domain;

import com.stamp.global.oauth.ProviderType;

public interface OAuthRequestProvider {
    ProviderType supportType();
    String provide();
}
