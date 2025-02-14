package com.stamp.api.auth.infra.oauth;

import com.stamp.api.auth.entity.OAuthId;

public record OAuthUserDetails(OAuthId oAuthId, String email) {}
