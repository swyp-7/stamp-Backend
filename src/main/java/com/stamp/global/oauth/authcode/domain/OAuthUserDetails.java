package com.stamp.global.oauth.authcode.domain;

import com.stamp.api.auth.domain.entity.OAuthId;

public record OAuthUserDetails(OAuthId oAuthId, String email) {}
