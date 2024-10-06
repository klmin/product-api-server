package com.product.auth.service;


import com.product.auth.dto.AuthGenerateTokenDTO;
import com.product.auth.response.AuthRefreshTokenResponse;
import com.product.auth.response.AuthTokenResponse;

public interface AuthenticationService {

    AuthTokenResponse generateToken(AuthGenerateTokenDTO dto);

    AuthRefreshTokenResponse refreshToken(String authorizationHeader);

    void logout(String authorizationHeader);

}
