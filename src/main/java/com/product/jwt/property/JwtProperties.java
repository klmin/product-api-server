package com.product.jwt.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("security.jwt")
@RequiredArgsConstructor
public class JwtProperties {
    private final String secretKey;
    private final long expirationTime;
    private final long refreshExpirationTime;
    private final String[] excludePath;
}
