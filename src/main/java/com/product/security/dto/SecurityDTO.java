package com.product.security.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Builder
public class SecurityDTO implements UserDetails {

    private Long userId;
    private String loginId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

}
