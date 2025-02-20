package com.stamp.api.auth.employer.controller;

import com.stamp.api.auth.employer.dto.request.LoginEmployerReq;
import com.stamp.api.auth.employer.dto.request.SocialLoginEmployerReq;
import com.stamp.api.auth.employer.dto.response.LoginRes;
import com.stamp.api.auth.employer.infra.oauth.ProviderType;
import com.stamp.api.auth.employer.service.AuthEmployerService;
import com.stamp.api.auth.employer.service.OAuthEmployerService;
import com.stamp.global.exception.DomainException;
import com.stamp.global.exception.GlobalErrorCode;
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
public class AuthEmployerController {

  private final AuthEmployerService authEmployerService;
  private final OAuthEmployerService oAuthEmployerService;

  @PostMapping("/auth/login")
  public ApplicationResponse<LoginRes> login(@RequestBody LoginEmployerReq loginEmployerReq) {
    return ApplicationResponse.ok(authEmployerService.login(loginEmployerReq));
  }

  @GetMapping("/oauth/{providerType}")
  public ApplicationResponse<Void> redirectAuthcodeUrl(
      @PathVariable ProviderType providerType, HttpServletResponse response) {
    String redirectUrl = oAuthEmployerService.getAuthCodeUrl(providerType);
    try {
      response.sendRedirect(redirectUrl);
    } catch (IOException e) {
      throw new DomainException(
          GlobalErrorCode.EXTERNAL_SERVICE_ERROR, "AuthController.redirectAuthcodeUrl");
    }
    return ApplicationResponse.ok();
  }

  @PostMapping("/oauth/login")
  public ApplicationResponse<LoginRes> login(@RequestBody SocialLoginEmployerReq loginReq) {
    return ApplicationResponse.ok(oAuthEmployerService.login(loginReq));
  }

  @PostMapping("/oauth/register")
  public ApplicationResponse<LoginRes> register(@RequestBody SocialLoginEmployerReq loginReq) {
    return ApplicationResponse.ok(oAuthEmployerService.registerNewUser(loginReq));
  }
}
