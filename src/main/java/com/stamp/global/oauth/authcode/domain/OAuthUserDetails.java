package com.stamp.global.oauth.authcode.domain;

import com.stamp.api.auth.domain.OAuthId;

public record OAuthUserDetails(
        OAuthId oAuthId,
        String nickname,
        String profileImageUrl
){
}
