package com.stamp.api.auth.controller;

import com.stamp.api.auth.domain.dto.SocialLoginReq;
import com.stamp.api.auth.service.OAuthService;
import com.stamp.global.oauth.ProviderType;
import com.stamp.global.response.ApplicationResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/{providerType}")
    public ApplicationResponse<Void> redirectAuthcodeUrl(
            @PathVariable ProviderType providerType,
            HttpServletResponse response
    ) {
        String redirectUrl = oAuthService.getAuthCodeUrl(providerType);
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("error : {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return ApplicationResponse.ok();
    }

    @PostMapping("/login")
    public ApplicationResponse<String> login(
            @RequestBody SocialLoginReq loginReq
    ) {
        return ApplicationResponse.ok(oAuthService.login(loginReq));
    }

}
