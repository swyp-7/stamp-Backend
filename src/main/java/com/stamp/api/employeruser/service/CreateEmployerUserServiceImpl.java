package com.stamp.api.employeruser.service;

import com.stamp.api.auth.exception.AuthErrorCode;
import com.stamp.api.employeruser.dto.CreateEmployerUserReq;
import com.stamp.api.employeruser.dto.CreateSocialEmployerUserReq;
import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.api.employeruser.repository.EmployerUserRepository;
import com.stamp.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateEmployerUserServiceImpl implements CreateEmployerUserService {
  private final EmployerUserRepository employerUserRepository;
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
  public EmployerUser createSocialEmployerUser(
      CreateSocialEmployerUserReq req, EmployerUser oauthEmployerUser) {
    EmployerUser user =
        EmployerUser.of(
            oauthEmployerUser.getOauthId(),
            req.name(),
            oauthEmployerUser.getEmail(),
            null,
            req.contact());
    return employerUserRepository.save(user);
  }
}
