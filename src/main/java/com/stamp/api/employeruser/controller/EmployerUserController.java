package com.stamp.api.employeruser.controller;

import com.stamp.api.employeruser.dto.response.EmployerUserRes;
import com.stamp.api.employeruser.service.ReadEmployerUserService;
import com.stamp.global.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/employer")
@RestController
public class EmployerUserController {

  private final ReadEmployerUserService readEmployerUserService;

  @GetMapping("/mypage")
  public ApplicationResponse<EmployerUserRes> getUserWithStore(
      @AuthenticationPrincipal UserDetails user) {
    // UserDetails.username에 사용자 Id값이 저장되어있어, 이를 활용하여 사용자 정보 조회 및 활용
    return ApplicationResponse.ok(
        readEmployerUserService.getUserWithStore(Long.valueOf(user.getUsername())));
  }
}
