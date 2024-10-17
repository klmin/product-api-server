package com.product.auth.service;

import com.product.util.ProductConstans;
import com.product.api.exception.ApiRuntimeException;
import com.product.auth.dto.AuthGenerateTokenDto;
import com.product.auth.response.AuthRefreshTokenResponse;
import com.product.auth.response.AuthTokenResponse;
import com.product.jwt.service.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationServiceImplTest {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtService jwtService;

    @Nested
    @DisplayName("토큰 생성 서비스 테스트")
    class generteToken{

        @DisplayName("토큰 생성 서비스 성공 테스트")
        @ParameterizedTest
        @MethodSource
        void success(String loginId, String password) {
            AuthGenerateTokenDto dto = buildGenerateToken(loginId, password);
            AuthTokenResponse response = authenticationService.generateToken(dto);

            assertNotNull(response.getAccessToken());
            assertNotNull(response.getAccessRefreshToken());
            assertEquals(response.getExpiresIn(), jwtService.getExpirationSecond());
            assertEquals(response.getRefreshExpiresIn(), jwtService.getRefreshExpirationSecond());
        }

        @DisplayName("토큰 생성 서비스 실패 테스트")
        @ParameterizedTest
        @MethodSource
        void fail(String loginId, String password, Class<? extends Throwable> clazz) {
            AuthGenerateTokenDto dto = buildGenerateToken(loginId, password);
            assertThrows(clazz, () -> authenticationService.generateToken(dto), "예외 발생 해야함");
        }

        private static Stream<Arguments> success() {
            return Stream.of(
                    Arguments.of("admin", "1234@aA!"),
                    Arguments.of("honggildong", "1234@aA!"),
                    Arguments.of("leesunsin", "1234@aA!")
            );
        }

        private static Stream<Arguments> fail() {
            return Stream.of(
                    Arguments.of("admin", "1235@aA!", BadCredentialsException.class),
                    Arguments.of("honggildong", "1434@aA!", BadCredentialsException.class),
                    Arguments.of("leesunsin", "1235@aA!", BadCredentialsException.class)
            );
        }

        private static AuthGenerateTokenDto buildGenerateToken(String loginId, String password) {
            return AuthGenerateTokenDto.builder().loginId(loginId).password(password).build();
        }
    }

    @Nested
    @DisplayName("리프레시 토큰 생성 서비스 테스트")
    class refreshToken {

        @DisplayName("리프레시 토큰 생성 서비스 성공 테스트")
        @ParameterizedTest
        @MethodSource
        void success(String loginId, String password) {

            AuthGenerateTokenDto dto = generteToken.buildGenerateToken(loginId, password);
            AuthTokenResponse authTokenResponse = authenticationService.generateToken(dto);
            String authorizationHeader = authTokenResponse.getAccessRefreshToken();
            AuthRefreshTokenResponse authRefreshTokenResponse = authenticationService.refreshToken(ProductConstans.BEARER_PREPIX +authorizationHeader);

            assertNotNull(authRefreshTokenResponse.getAccessToken());
            assertNotNull(authRefreshTokenResponse.getAccessRefreshToken());
            assertEquals(authRefreshTokenResponse.getExpiresIn(), jwtService.getExpirationSecond());
            assertEquals(authRefreshTokenResponse.getRefreshExpiresIn(), jwtService.getRefreshExpirationSecond());

        }

        @DisplayName("리프레시 토큰 생성 서비스 실패 테스트")
        @Test
        void fail() {

            AuthGenerateTokenDto dto = generteToken.buildGenerateToken("admin", "1234@aA!");
            AuthTokenResponse authTokenResponse = authenticationService.generateToken(dto);

            ApiRuntimeException exception = assertThrows(ApiRuntimeException.class,
                    () -> authenticationService.refreshToken(ProductConstans.BEARER_PREPIX +authTokenResponse.getAccessToken()));

            assertEquals("토큰이 유효하지 않습니다.", exception.getMessage());
            assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());

            exception = assertThrows(ApiRuntimeException.class,
                    () -> authenticationService.refreshToken(authTokenResponse.getAccessRefreshToken()));

            assertEquals("헤더가 올바르지 않습니다.", exception.getMessage());
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        }

        private static Stream<Arguments> success() {
            return Stream.of(
                    Arguments.of("admin", "1234@aA!"),
                    Arguments.of("leesunsin", "1234@aA!"),
                    Arguments.of("ganggamchan", "1234@aA!")
            );
        }

    }


    @Nested
    @DisplayName("로그아웃 서비스 테스트")
    class logout {

        @DisplayName("로그아웃 서비스 성공 테스트")
        @ParameterizedTest
        @MethodSource
        void success(String loginId, String password) {

            AuthGenerateTokenDto dto = generteToken.buildGenerateToken(loginId, password);
            AuthTokenResponse authTokenResponse = authenticationService.generateToken(dto);
            AuthRefreshTokenResponse authRefreshTokenResponse = authenticationService.refreshToken(ProductConstans.BEARER_PREPIX +authTokenResponse.getAccessRefreshToken());

            assertNotNull(authRefreshTokenResponse.getAccessToken());
            assertNotNull(authRefreshTokenResponse.getAccessRefreshToken());
            assertEquals(authRefreshTokenResponse.getExpiresIn(), jwtService.getExpirationSecond());
            assertEquals(authRefreshTokenResponse.getRefreshExpiresIn(), jwtService.getRefreshExpirationSecond());

            authenticationService.logout(ProductConstans.BEARER_PREPIX +authTokenResponse.getAccessToken());

            ApiRuntimeException exception = assertThrows(ApiRuntimeException.class,
                    () -> authenticationService.refreshToken(ProductConstans.BEARER_PREPIX +authTokenResponse.getAccessRefreshToken()));

            assertEquals("토큰이 유효하지 않습니다.", exception.getMessage());
            assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());

        }

        @DisplayName("로그아웃 서비스 실패 테스트")
        @Test
        void fail() {

            AuthGenerateTokenDto dto = generteToken.buildGenerateToken("admin", "1234@aA!");
            AuthTokenResponse authTokenResponse = authenticationService.generateToken(dto);

            ApiRuntimeException exception = assertThrows(ApiRuntimeException.class,
                    () -> authenticationService.logout(ProductConstans.BEARER_PREPIX +authTokenResponse.getAccessRefreshToken()));

            assertEquals("토큰이 유효하지 않습니다.", exception.getMessage());
            assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());

            exception = assertThrows(ApiRuntimeException.class,
                    () -> authenticationService.logout(authTokenResponse.getAccessToken()));

            assertEquals("헤더가 올바르지 않습니다.", exception.getMessage());
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

            exception = assertThrows(ApiRuntimeException.class,
                    () -> authenticationService.logout(null));

            assertEquals("헤더가 올바르지 않습니다.", exception.getMessage());
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        }

        private static Stream<Arguments> success() {
            return Stream.of(
                    Arguments.of("admin", "1234@aA!"),
                    Arguments.of("honggildong", "1234@aA!"),
                    Arguments.of("leesunsin", "1234@aA!")
            );
        }

    }

}