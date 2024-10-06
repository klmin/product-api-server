package com.product.auth.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthTokenResponse extends TokenResponse{

}
