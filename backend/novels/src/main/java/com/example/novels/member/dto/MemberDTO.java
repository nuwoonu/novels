package com.example.novels.member.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.novels.member.entity.constant.MemberRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter

public class MemberDTO extends User /* implements OAuth2User */ {

    // member Entity 정보 + 인증 정보

    private String email;

    private String pw;

    private String name;

    private String nickname;

    private boolean fromSocial;

    private List<String> roles = new ArrayList<>();

    // OAuth2User 가 넘겨주는 attr 담기 위해
    private Map<String, Object> attr;

    public MemberDTO(String username, String pw, String nickname, boolean fromSocial,
            List<String> roles) {

        super(username, pw, roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList());
        this.fromSocial = fromSocial;
        this.email = username;
        this.pw = pw;
        this.nickname = nickname;
        this.roles = roles;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", email);
        dataMap.put("pw", pw);
        dataMap.put("nickname", nickname);
        dataMap.put("social", fromSocial);
        dataMap.put("roles", roles);
        return dataMap;

    }

    // // OAuth2User
    // public MemberDTO(String username, String pw, boolean fromSocial,
    // Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr)
    // {
    // this(username, pw, fromSocial, authorities);
    // this.attr = attr;
    // }

    // @Override
    // public Map<String, Object> getAttributes() {
    // return this.attr;
    // }
}
