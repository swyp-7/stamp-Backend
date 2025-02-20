package com.stamp.api.signup.employer.controller;

import com.stamp.api.signup.employer.dto.request.SignUpEmployerReq;
import com.stamp.api.signup.employer.service.SignUpEmployerService;
import com.stamp.global.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class SignUpEmployerController {

  private final SignUpEmployerService signUpEmployerService;

  @PostMapping("/signUp")
  public ApplicationResponse<Void> signUp(@RequestBody SignUpEmployerReq signUpEmployerReq) {
    signUpEmployerService.signUp(signUpEmployerReq);
    return ApplicationResponse.ok();
  }
}
