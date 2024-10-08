package com.product.auth.service;


import com.product.auth.dto.AuthGenerateTokenDto;
import com.product.auth.response.AuthRefreshTokenResponse;
import com.product.auth.response.AuthTokenResponse;

public interface AuthenticationService {

    AuthTokenResponse generateToken(AuthGenerateTokenDto dto);

    AuthRefreshTokenResponse refreshToken(String authorizationHeader);

    void logout(String authorizationHeader);

}
