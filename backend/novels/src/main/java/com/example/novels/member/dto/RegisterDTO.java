package com.example.novels.member.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter

public class RegisterDTO {

    // member Entity 정보 + 인증 정보
    @Email(message = "이메일 확인")
    @NotBlank(message = "필수입력요소")
    private String email;
    @NotBlank(message = "필수입력요소")
    private String password;
    @NotBlank(message = "필수입력요소")
    private String nickname;

    private boolean fromSocial;

}
