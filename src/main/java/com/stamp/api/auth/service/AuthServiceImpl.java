package com.stamp.api.auth.service;

import com.stamp.api.auth.dto.request.LoginEmployerReq;
import com.stamp.api.auth.dto.response.LoginRes;
import com.stamp.api.auth.exception.AuthErrorCode;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.employeruser.repository.EmployerUserRepository;
import com.stamp.global.exception.DomainException;
import com.stamp.global.jwt.JwtResponse;
import com.stamp.global.jwt.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
  private final EmployerUserRepository employerUserRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  @Override
  public LoginRes login(LoginEmployerReq loginEmployerReq) {
    EmployerUser employerUser =
        employerUserRepository
            .findByEmail(loginEmployerReq.email())
            .orElseThrow(
                () ->
                    new DomainException(
                        AuthErrorCode.EMPLOYER_USER_NOT_FOUNDED, "AuthServiceImpl.login"));
    if (!passwordEncoder.matches(loginEmployerReq.password(), employerUser.getPassword())) {
      throw new DomainException(AuthErrorCode.INVALID_PASSWORD, "AuthServiceImpl.login");
    }
    JwtResponse response = jwtTokenProvider.generateToken(employerUser);
    return LoginRes.of(response.token(), response.expiration());
  }
}
