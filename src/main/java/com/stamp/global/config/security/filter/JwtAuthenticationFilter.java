package com.stamp.global.config.security.filter;

import com.stamp.global.jwt.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    // Request Method, URI 로깅
    String requestUri = request.getRequestURI();
    String method = request.getMethod();
    log.info("Request method: {}, Request URI : {}", method,requestUri);

    String token = extractToken(request);

    if (token != null) {
      try {
        // JWT에서 사용자 정보를 가져오는 메서드
        String userId = jwtTokenProvider.validateAndGetUserId(token);
        String role = jwtTokenProvider.getClaims(token).get("roles").toString();

        UserDetails userDetails = User.builder().username(userId).password("").roles(role).build();

        JwtAuthentication authentication =
            new JwtAuthentication(userDetails, token, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        log.error("JWT 검증 실패: {}", e.getMessage());
      }
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
