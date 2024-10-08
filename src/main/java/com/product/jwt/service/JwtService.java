package com.product.jwt.service;


import com.product.util.ProductConstans;
import com.product.jwt.claims.JwtClaims;
import com.product.jwt.enums.JwtTokenType;
import com.product.jwt.property.JwtProperties;
import com.product.security.dto.SecurityDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final String TOKEN_TYPE = "token_type";
    private final String ROLES = "roles";

    private final JwtProperties jwtProperties;

    public long getExpirationSecond(){
        return jwtProperties.getExpirationTime()/1000;
    }
    public long getRefreshExpirationSecond(){
        return jwtProperties.getRefreshExpirationTime()/1000;
    }

    public long getExpirationTime(){
        return jwtProperties.getExpirationTime();
    }
    public long getRefreshExpirationTime(){
        return jwtProperties.getRefreshExpirationTime();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public JwtBuilder jwtbuilder(SecurityDto securityDto, long time, String tokenType){
        return Jwts.builder()
                .claim(TOKEN_TYPE, tokenType)
                .subject(String.valueOf(securityDto.getUserId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + time))
                .signWith(getSignInKey())
                .header().type("jwt")
                .and();
    }

    public String generateToken(SecurityDto securityDto){
        return jwtbuilder(securityDto, getExpirationTime(), JwtTokenType.ACCESS.getName())
                .claim(ROLES, securityDto.getAuthorities().stream().map(Objects::toString).toList())
                .compact();
    }

    public String generateRefreshToken(SecurityDto securityDto){
        return jwtbuilder(securityDto, getRefreshExpirationTime(), JwtTokenType.REFRESH.getName()).compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @SuppressWarnings("unchecked")
    public JwtClaims getJwtClaims(String token){
        Claims claims = this.extractAllClaims(token);
        return JwtClaims.builder()
                .userId(claims.getSubject())
                .tokenType(JwtTokenType.valueOf(claims.get(TOKEN_TYPE, String.class).toUpperCase()))
                .roles(claims.get(ROLES, List.class))
                .expiration(claims.getExpiration())
                .build();
    }

    public boolean isInvalidAuthorizationHeader(String header){
        return !StringUtils.hasText(header) || !header.startsWith(ProductConstans.BEARER_PREPIX);
    }

    public boolean isAccessToken(JwtTokenType tokenType){
        return JwtTokenType.ACCESS.equals(tokenType);
    }

}
