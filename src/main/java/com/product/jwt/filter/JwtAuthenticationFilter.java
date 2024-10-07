package com.product.jwt.filter;


import com.product.jwt.claims.JwtClaims;
import com.product.jwt.enums.JwtTokenType;
import com.product.jwt.property.JwtProperties;
import com.product.jwt.service.JwtService;
import com.product.redis.constants.RedisCacheNames;
import com.product.redis.service.RedisService;
import com.product.security.dto.SecurityDTO;
import com.product.security.service.SecurityService;
import com.product.util.ProductConstans;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final SecurityService securityService;
    private final JwtService jwtService;
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(ProductConstans.AUTHORIZATION_HEADER);
        boolean isInvalid = jwtService.isInvalidAuthorizationHeader(authorizationHeader);

        if(isInvalid){
            filterChain.doFilter(request, response);
            return;
        }

        try {

            String token = authorizationHeader.substring(7);

            String logoutToken = redisService.get(RedisCacheNames.generateJwtBlackListTokenCacheKey(token));

            if(logoutToken != null){
                log.error("[logout Token] token : {}, userId : {}", token, logoutToken);
                filterChain.doFilter(request, response);
                return;
            }

            JwtClaims jwtClaims = jwtService.getJwtClaims(token);
            Long userId = Long.parseLong(jwtClaims.getUserId());
            Collection<String> roles = jwtClaims.getRoles();
            JwtTokenType tokenType = jwtClaims.getTokenType();

            if(!jwtService.isAccessToken(jwtClaims.getTokenType())){
                log.error("[Not Access Token] token : {}, userId : {}, tokenType : {}", token, userId, tokenType);
                filterChain.doFilter(request, response);
                return;
            }

            boolean isActive = securityService.isActive(userId);

            if(!isActive){
                log.error("[Not ACTIVE USER] token : {}, userId : {}", token, userId);
                filterChain.doFilter(request, response);
                return;
            }

            Collection<GrantedAuthority> authorities = securityService.getGrantedAuthorities(roles);

            log.info("authorities : {}", authorities);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                SecurityDTO.builder()
                           .userId(userId)
                           .authorities(authorities)
                           .build(),
                           null,
                            authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(jwtProperties.getExcludePath()).anyMatch(request.getRequestURI()::startsWith);
    }
}
