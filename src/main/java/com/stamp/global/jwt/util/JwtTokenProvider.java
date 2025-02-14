package com.stamp.global.jwt.util;

import com.stamp.api.employeruser.entity.EmployerUser;
import com.stamp.global.jwt.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
  private final SecretKey secretKey;

  public JwtTokenProvider(@Value("${jwt.secret_key}") String jwtSecret) {
    byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
  }

  public JwtResponse generateToken(EmployerUser member) {
    long expirationMs = 1000 * 60 * 120;
    return new JwtResponse(
        Jwts.builder()
            .subject(String.valueOf(member.getId()))
            .claim("email", member.getEmail())
            .claim("contact", member.getContact())
            .claim("roles", "Employer")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(secretKey)
            .compact(),
        expirationMs);
  }

  public String validateAndGetUserId(String token) {
    Claims claims =
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

    return claims.getSubject();
  }
}
