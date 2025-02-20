package com.stamp.api.auth.employer.infra.oauth;

import com.stamp.api.auth.employer.entity.OAuthId;

public record OAuthUserDetails(OAuthId oAuthId, String email) {}
