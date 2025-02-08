package com.stamp.api.auth.repository;

import com.stamp.api.auth.domain.OAuthId;
import com.stamp.api.auth.domain.EmployerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerUserRepository extends JpaRepository<EmployerUser, Long> {

    Optional<EmployerUser> findByOauthId(OAuthId oauthId);
}
