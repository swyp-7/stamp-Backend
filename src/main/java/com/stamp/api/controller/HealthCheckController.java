package com.stamp.api.controller;

import com.stamp.api.auth.domain.entity.EmployerUser;
import com.stamp.api.auth.repository.EmployerUserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class HealthCheckController {

    private final EmployerUserRepository employerUserRepository;

    @GetMapping("/healthCheck")
    public String healthCheck() {
        LocalDateTime now = LocalDateTime.now();
        return "server is Up! requested at:" + now;
    }

    @GetMapping("/authenticated-healthCheck")
    public String authenticatedHealthCheck(@AuthenticationPrincipal UserDetails user) {
        log.info(user.toString());
        EmployerUser employerUser = employerUserRepository
                .findById(Long.valueOf(user.getUsername()))
                .orElseGet(null);
        log.info(String.valueOf(employerUser));
        LocalDateTime now = LocalDateTime.now();
        return "server is Up! requested at:" + now;
    }
}
