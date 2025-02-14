package com.stamp.api.signup.controller;

import com.stamp.api.signup.dto.request.SignUpReq;
import com.stamp.api.signup.service.SignUpService;
import com.stamp.global.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class SignUpController {

  private final SignUpService signUpService;

  @PostMapping("/signUp")
  public ApplicationResponse<Void> signUp(@RequestBody SignUpReq signUpReq) {
    signUpService.signUp(signUpReq);
    return ApplicationResponse.ok();
  }
}
