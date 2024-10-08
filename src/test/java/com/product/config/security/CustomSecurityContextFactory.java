package com.product.config.security;

import com.product.security.dto.SecurityDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;

public class CustomSecurityContextFactory implements WithSecurityContextFactory<WithMockUserCustom> {
    @Override
    public SecurityContext createSecurityContext(WithMockUserCustom annotation) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        SecurityDto securityDto = SecurityDto.builder()
                .userId(annotation.userId())
                .loginId(annotation.loginId())
                .username(annotation.username())
                .password(annotation.password())
                .authorities(Arrays.stream(annotation.roles())
                        .map(SimpleGrantedAuthority::new)
                        .toList())
                .build();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(securityDto, null, securityDto.getAuthorities());
        context.setAuthentication(authToken);

        return context;
    }
}
