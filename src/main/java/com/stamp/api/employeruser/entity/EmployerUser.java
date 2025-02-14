package com.stamp.api.employeruser.entity;

import com.stamp.api.auth.entity.OAuthId;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(
    name = "employer_user",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "oauth_id_unique",
          columnNames = {"oauth_server_id", "oauth_server_provider"}),
    })
public class EmployerUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded private OAuthId oauthId;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  private String password; // 소셜 로그인 사용자는 null
  private String contact;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  private EmployerUser(
      @Nullable OAuthId oauthId, String name, String email, String password, String contact) {
    this.oauthId = oauthId;
    this.name = name;
    this.email = email;
    this.password = password;
    this.contact = contact;
  }

  public static EmployerUser of(
      String name, String email, String password, String contact) { // 기본 사용자 팩토리 메소드 패턴
    return new EmployerUser(null, name, email, password, contact);
  }

  public static EmployerUser of(
      OAuthId oauthId,
      String name,
      String email,
      String password,
      String contact) { // 소셜 로그인 사용자 팩토리 메소드 패턴
    return new EmployerUser(oauthId, name, email, password, contact);
  }
}
