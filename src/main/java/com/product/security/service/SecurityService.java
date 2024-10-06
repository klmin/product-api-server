package com.product.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface SecurityService extends UserDetailsService {

    boolean isActive(Long id);

    Collection<GrantedAuthority> getGrantedAuthorities(Collection<String> authorities);

}
