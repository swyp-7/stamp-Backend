package com.stamp.api.employeruser.repository;

import com.stamp.api.auth.employer.entity.OAuthId;
import com.stamp.api.employeruser.entity.EmployerUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerUserRepository extends JpaRepository<EmployerUser, Long> {
  Optional<EmployerUser> findByOauthId(OAuthId oauthId);

  Optional<EmployerUser> findByEmail(String email);
}
