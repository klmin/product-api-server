package com.product.auth.service;

import com.product.api.exception.ApiRuntimeException;
import com.product.auth.dto.AuthGenerateTokenDTO;
import com.product.auth.response.AuthRefreshTokenResponse;
import com.product.auth.response.AuthTokenResponse;
import com.product.auth.response.TokenResponse;
import com.product.jwt.claims.JwtClaims;
import com.product.jwt.enums.JwtTokenType;
import com.product.jwt.service.JwtService;
import com.product.redis.constants.RedisCacheNames;
import com.product.redis.service.RedisService;
import com.product.security.dto.SecurityDTO;
import com.product.security.service.SecurityService;
import com.product.user.projection.LoginIdProjection;
import com.product.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RedisService redisService;
    private final SecurityService securityService;
    private final UserService userService;
    private final String UNAUTHORIZAED_MESSAGE = "토큰이 유효하지 않습니다.";

    public SecurityDTO authenticate(String loginId, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginId,
                        password
                )
        );

        return (SecurityDTO) authentication.getPrincipal();
    }

    @Override
    public AuthTokenResponse generateToken(AuthGenerateTokenDTO dto) {

        String loginId = dto.getLoginId();
        SecurityDTO securityDTO = authenticate(loginId, dto.getPassword());

        TokenResponse tokenResponse = buildAuthTokenResponse(securityDTO);

        return AuthTokenResponse.builder()
                .accessToken(tokenResponse.getAccessToken())
                .accessRefreshToken(tokenResponse.getAccessRefreshToken())
                .expiresIn(tokenResponse.getExpiresIn())
                .refreshExpiresIn(tokenResponse.getRefreshExpiresIn())
                .build();
    }


    @Override
    public AuthRefreshTokenResponse refreshToken(String authorizationHeader) {

        String token = validateAndExtractToken(authorizationHeader);

        JwtClaims jwtClaims = jwtService.getJwtClaims(token);

        if(isAccessToken(jwtClaims.getTokenType())){
            throw new ApiRuntimeException(HttpStatus.UNAUTHORIZED, UNAUTHORIZAED_MESSAGE);
        }

        Long userId = Long.parseLong(jwtClaims.getUserId());

        boolean isRefreshToken = redisService.hasKey(RedisCacheNames.generateJwtRefreshTokenCacheKey(userId));

        if(!isRefreshToken){
            throw new ApiRuntimeException(HttpStatus.UNAUTHORIZED, UNAUTHORIZAED_MESSAGE);
        }

        LoginIdProjection loginIdProjection = userService.findByUserId(userId, LoginIdProjection.class);

        SecurityDTO securityDTO = (SecurityDTO) securityService.loadUserByUsername(loginIdProjection.loginId());

        TokenResponse tokenResponse = buildAuthTokenResponse(securityDTO);

        return AuthRefreshTokenResponse.builder()
                .accessToken(tokenResponse.getAccessToken())
                .accessRefreshToken(tokenResponse.getAccessRefreshToken())
                .expiresIn(tokenResponse.getExpiresIn())
                .refreshExpiresIn(tokenResponse.getRefreshExpiresIn())
                .build();
    }

    @Override
    public void logout(String authorizationHeader) {

        String token = validateAndExtractToken(authorizationHeader);

        JwtClaims jwtClaims = jwtService.getJwtClaims(token);

        if(!isAccessToken(jwtClaims.getTokenType())){
            throw new ApiRuntimeException(HttpStatus.UNAUTHORIZED, UNAUTHORIZAED_MESSAGE);
        }

        Long userId = Long.parseLong(jwtClaims.getUserId());

        long now = System.currentTimeMillis();
        long expiration = jwtClaims.getExpiration().getTime();
        long ttl = (expiration - now) / 1000;

        redisService.insert(RedisCacheNames.generateJwtBlackListTokenCacheKey(token), jwtClaims.getUserId(), ttl, TimeUnit.SECONDS);
        redisService.delete(RedisCacheNames.generateJwtRefreshTokenCacheKey(userId));

    }

    private String validateAndExtractToken(String authorizationHeader) {
        boolean isInvalid = jwtService.isInvalidAuthorizationHeader(authorizationHeader);

        if (isInvalid) {
            throw new ApiRuntimeException(HttpStatus.BAD_REQUEST, "헤더가 올바르지 않습니다.");
        }
        return authorizationHeader.substring(7);
    }

    public void insertRefreshToken(Long userId, String refreshToken){
        String key = RedisCacheNames.generateJwtRefreshTokenCacheKey(userId);
        redisService.insert(key, refreshToken, jwtService.getRefreshExpirationSecond(), TimeUnit.SECONDS);
    }

    private TokenResponse buildAuthTokenResponse(SecurityDTO securityDTO) {
        String accessToken = jwtService.generateToken(securityDTO);
        String refreshToken = jwtService.generateRefreshToken(securityDTO);

        this.insertRefreshToken(securityDTO.getUserId(), refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .accessRefreshToken(refreshToken)
                .expiresIn(jwtService.getExpirationSecond())
                .refreshExpiresIn(jwtService.getRefreshExpirationSecond())
                .build();
    }

    public boolean isAccessToken(JwtTokenType tokenType){
        return JwtTokenType.ACCESS.equals(tokenType);
    }

}
