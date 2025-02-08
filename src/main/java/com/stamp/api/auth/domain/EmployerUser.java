package com.stamp.api.auth.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@ToString
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "employer_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "oauth_id_unique",
                        columnNames = {
                                "oauth_server_id",
                                "oauth_server_provider"
                        }
                ),
        }
)
public class EmployerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    @Embedded
    private OAuthId oauthId;
    private String name;
    private String email;
//    private String password; 인증은 나중에 암호화랑 함께 추가?
    private String contact;
    @Nullable
    private String nickname;
    @Nullable
    private String profileImageUrl;

    /**
     * 일반 사용자와 소셜 로그인 사용자를 구분하기 위한?
     *
     * @Enumerated(EnumType.STRING) private SignupMethod signupMethod; // 가입 방식
     **/


    public static EmployerUser of(OAuthId oauthId, String name, String email, String contact, String nickname, String profileImageUrl) {
        return new EmployerUser(null, oauthId, name, email, contact, nickname, profileImageUrl);
    }
}
