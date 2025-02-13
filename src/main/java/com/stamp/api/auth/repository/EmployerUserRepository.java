package com.stamp.api.auth.repository;

import com.stamp.api.auth.domain.entity.EmployerUser;
import com.stamp.api.auth.domain.entity.OAuthId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerUserRepository extends JpaRepository<EmployerUser, Long> {
  Optional<EmployerUser> findByOauthId(OAuthId oauthId);

  Optional<EmployerUser> findByEmail(String email);
}
