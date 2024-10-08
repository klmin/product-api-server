package com.product.jpa.config;

import com.product.security.dto.SecurityDto;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditConfig implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authenticationToken =  SecurityContextHolder.getContext().getAuthentication();

        if(authenticationToken == null || !authenticationToken.isAuthenticated() || authenticationToken.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }

        SecurityDto securityDto = (SecurityDto) authenticationToken.getPrincipal();

        return Optional.of(securityDto.getUserId());
    }
}
