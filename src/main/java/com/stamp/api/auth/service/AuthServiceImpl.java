package com.stamp.api.auth.service;

import com.stamp.api.auth.domain.dto.request.LoginReq;
import com.stamp.api.auth.domain.dto.response.LoginRes;
import com.stamp.api.auth.domain.entity.EmployerUser;
import com.stamp.api.auth.exception.AuthErrorCode;
import com.stamp.api.auth.repository.EmployerUserRepository;
import com.stamp.api.signup.domain.dto.request.CreateEmployerUserReq;
import com.stamp.global.exception.DomainException;
import com.stamp.global.util.JwtResponse;
import com.stamp.global.util.JwtTokenProvider;
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
  public EmployerUser createEmployerUser(CreateEmployerUserReq createEmployerUserReq) {
    employerUserRepository
        .findByEmail(createEmployerUserReq.email())
        .ifPresent(
            user -> {
              log.error("email already existed");
              throw new DomainException(
                  AuthErrorCode.EMAIL_ALREADY_EXISTED, "AuthServiceImpl.signUp");
            });

    String encryptedPassword = passwordEncoder.encode(createEmployerUserReq.password());

    return employerUserRepository.save(
        EmployerUser.of(
            createEmployerUserReq.name(),
            createEmployerUserReq.email(),
            encryptedPassword,
            createEmployerUserReq.contact()));
  }

  @Override
  public LoginRes login(LoginReq loginReq) {
    EmployerUser employerUser =
        employerUserRepository
            .findByEmail(loginReq.email())
            .orElseThrow(
                () ->
                    new DomainException(
                        AuthErrorCode.EMPLOYER_USER_NOT_FOUNDED, "AuthServiceImpl.login"));
    if (!passwordEncoder.matches(loginReq.password(), employerUser.getPassword())) {
      throw new DomainException(AuthErrorCode.INVALID_PASSWORD, "AuthServiceImpl.login");
    }
    JwtResponse response = jwtTokenProvider.generateToken(employerUser);
    return LoginRes.of(response.token(), response.expiration());
  }
}
