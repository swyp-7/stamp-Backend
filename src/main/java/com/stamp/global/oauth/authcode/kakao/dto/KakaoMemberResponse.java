package com.stamp.global.oauth.authcode.kakao.dto;

import static com.stamp.global.oauth.ProviderType.KAKAO;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.stamp.api.auth.entity.OAuthId;
import com.stamp.global.oauth.authcode.OAuthUserDetails;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonNaming(SnakeCaseStrategy.class)
public record KakaoMemberResponse(
    Long id, boolean hasSignedUp, LocalDateTime connectedAt, KakaoAccount kakaoAccount) {

  public OAuthUserDetails toOAuthUser() {
    log.info(kakaoAccount.toString());
    return new OAuthUserDetails(new OAuthId(String.valueOf(id), KAKAO), kakaoAccount.email());
  }

  @JsonNaming(SnakeCaseStrategy.class)
  public record KakaoAccount(
      boolean profileNeedsAgreement,
      boolean profileNicknameNeedsAgreement,
      boolean profileImageNeedsAgreement,
      Profile profile,
      boolean nameNeedsAgreement,
      String name,
      boolean emailNeedsAgreement,
      boolean isEmailValid,
      boolean isEmailVerified,
      String email,
      boolean ageRangeNeedsAgreement,
      String ageRange,
      boolean birthyearNeedsAgreement,
      String birthyear,
      boolean birthdayNeedsAgreement,
      String birthday,
      String birthdayType,
      boolean genderNeedsAgreement,
      String gender,
      boolean phoneNumberNeedsAgreement,
      String phoneNumber,
      boolean ciNeedsAgreement,
      String ci,
      LocalDateTime ciAuthenticatedAt) {}

  @JsonNaming(SnakeCaseStrategy.class)
  public record Profile(
      String nickname, String thumbnailImageUrl, String profileImageUrl, boolean isDefaultImage) {}
}
