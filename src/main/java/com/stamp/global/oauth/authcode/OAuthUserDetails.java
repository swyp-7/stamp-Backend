package com.stamp.global.oauth.authcode;

import com.stamp.api.auth.entity.OAuthId;

public record OAuthUserDetails(OAuthId oAuthId, String email) {}
