package com.product.auth.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class TokenResponse {
    private String accessToken;
    private String accessRefreshToken;
    private long expiresIn;
    private long refreshExpiresIn;
}
