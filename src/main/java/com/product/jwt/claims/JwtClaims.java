package com.product.jwt.claims;

import com.product.jwt.enums.JwtTokenType;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.Date;

@Builder
@Getter
public class JwtClaims  {

    private String userId;
    private JwtTokenType tokenType;
    private Collection<String> roles;
    private Date expiration;

}
