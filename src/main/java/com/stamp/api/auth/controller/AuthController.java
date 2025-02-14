package com.stamp.api.auth.controller;

import com.stamp.api.auth.dto.request.LoginReq;
import com.stamp.api.auth.dto.request.SocialLoginReq;
import com.stamp.api.auth.dto.response.LoginRes;
import com.stamp.api.auth.service.AuthService;
import com.stamp.api.auth.service.OAuthService;
import com.stamp.api.auth.infra.oauth.ProviderType;
import com.stamp.global.response.ApplicationResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AuthController {

  private final AuthService authService;
  private final OAuthService oAuthService;

  @PostMapping("/auth/login")
  public ApplicationResponse<LoginRes> login(@RequestBody LoginReq loginReq) {
    return ApplicationResponse.ok(authService.login(loginReq));
  }

  @GetMapping("/oauth/{providerType}")
  public ApplicationResponse<Void> redirectAuthcodeUrl(
      @PathVariable ProviderType providerType, HttpServletResponse response) {
    String redirectUrl = oAuthService.getAuthCodeUrl(providerType);
    try {
      response.sendRedirect(redirectUrl);
    } catch (IOException e) {
      log.error("error : {}", e.getMessage());
      throw new RuntimeException(e);
    }
    return ApplicationResponse.ok();
  }

  @PostMapping("/oauth/login")
  public ApplicationResponse<LoginRes> login(@RequestBody SocialLoginReq loginReq) {
    return ApplicationResponse.ok(oAuthService.login(loginReq));
  }
}
