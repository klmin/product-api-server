package com.product.auth.controller;

import com.product.util.ProductConstans;
import com.product.api.response.ApiResponse;
import com.product.auth.request.AuthLoginRequest;
import com.product.auth.response.AuthRefreshTokenResponse;
import com.product.auth.response.AuthTokenResponse;
import com.product.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthTokenResponse>> login(@RequestBody @Valid AuthLoginRequest request)  {
        AuthTokenResponse response = service.generateToken(request.toTokenDTO());
        return ApiResponse.success(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthRefreshTokenResponse>> refreshToken(@RequestHeader(ProductConstans.AUTHORIZATION_HEADER) String authorizationHeader)  {
        AuthRefreshTokenResponse response = service.refreshToken(authorizationHeader);
        return ApiResponse.success(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader(ProductConstans.AUTHORIZATION_HEADER) String authorizationHeader)  {
        service.logout(authorizationHeader);
        return ApiResponse.success();
    }


}
