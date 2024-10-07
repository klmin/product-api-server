package com.product.auth.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.product.util.ProductConstans;
import com.product.api.response.ApiResponse;
import com.product.auth.request.AuthLoginRequest;
import com.product.auth.response.AuthTokenResponse;
import com.product.config.abstracts.AbstractMvcTest;
import com.product.jwt.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class AuthControllerTest extends AbstractMvcTest {

    @Autowired
    JwtService jwtService;

    @Nested
    @DisplayName("로그인 컨트롤러 테스트")
    class login{

        @DisplayName("로그인 컨트롤러 성공 테스트")
        @ParameterizedTest
        @MethodSource
        void success (String loginId, String password, HttpStatus status) throws Exception {

            AuthLoginRequest request = buildAuthLoginRequest(loginId, password);

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value(status.getReasonPhrase()))
                    .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                    .andExpect(jsonPath("$.data.accessRefreshToken").isNotEmpty())
                    .andExpect(jsonPath("$.data.expiresIn").value(jwtService.getExpirationSecond()))
                    .andExpect(jsonPath("$.data.refreshExpiresIn").value(jwtService.getRefreshExpirationSecond()))
            ;

        }

        @DisplayName("로그인 컨트롤러 실패 테스트")
        @ParameterizedTest
        @MethodSource
        void fail (String loginId, String password, HttpStatus status) throws Exception {

            AuthLoginRequest request = buildAuthLoginRequest(loginId, password);

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.code").value(status.value()))
                    .andExpect(jsonPath("$.message").value(status.getReasonPhrase()))
            ;

        }

        private static Stream<Arguments> success() {
            return Stream.of(
                    Arguments.of("01012345678", "1234@aA!", HttpStatus.OK),
                    Arguments.of("01022345678", "1234@aA!", HttpStatus.OK),
                    Arguments.of("01012345679 ", "1234@aA!", HttpStatus.OK),
                    Arguments.of("admin", "1234@aA!", HttpStatus.OK)
            );
        }


        private static Stream<Arguments> fail() {
            return Stream.of(
                    Arguments.of("010123456", "1234@aA!", HttpStatus.UNAUTHORIZED),
                    Arguments.of("admin2", "1234@aA!", HttpStatus.UNAUTHORIZED),
                    Arguments.of("01099345679", "1234@aA!", HttpStatus.UNAUTHORIZED)
            );
        }

        private static AuthLoginRequest buildAuthLoginRequest(String loginId, String password) {
            return AuthLoginRequest.builder().loginId(loginId).password(password).build();
        }

    }

    @Nested
    @DisplayName("로그인")
    class authLogin{

        public String accessToken = "";
        public String refreshToken = "";

        @BeforeEach
        void login() throws Exception {

            AuthLoginRequest request = AuthControllerTest.login.buildAuthLoginRequest("admin", "1234@aA!");

            MvcResult result = mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andReturn();

            String jsonResponse = result.getResponse().getContentAsString();
            ApiResponse<AuthTokenResponse> responseDTO = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            accessToken = responseDTO.getData().getAccessToken();
            refreshToken = responseDTO.getData().getAccessRefreshToken();

        }

        @Nested
        @DisplayName("리프레쉬 토큰 컨트롤러 테스트")
        class refreshToken{

            @Test
            @DisplayName("리프레쉬 토큰 컨트롤러 성공 테스트")
            void success() throws Exception {

                mockMvc.perform(post("/api/auth/refresh-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(ProductConstans.AUTHORIZATION_HEADER, ProductConstans.BEARER_PREPIX+refreshToken)
                        )
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()))
                        .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                        .andExpect(jsonPath("$.data.accessRefreshToken").isNotEmpty())
                        .andExpect(jsonPath("$.data.expiresIn").value(jwtService.getExpirationSecond()))
                        .andExpect(jsonPath("$.data.refreshExpiresIn").value(jwtService.getRefreshExpirationSecond()))
                ;

            }

            @DisplayName("리프레쉬 토큰 컨트롤러 실패 테스트")
            @Test
            void fail() throws Exception {

                mockMvc.perform(post("/api/auth/refresh-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(ProductConstans.AUTHORIZATION_HEADER, ProductConstans.BEARER_PREPIX+accessToken)
                        )
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                        .andExpect(jsonPath("$.code").value(HttpStatus.UNAUTHORIZED.value()))
                        .andExpect(jsonPath("$.message").value("토큰이 유효하지 않습니다."))
                        .andExpect(jsonPath("$.data").isEmpty())
                ;

                mockMvc.perform(post("/api/auth/refresh-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("test", ProductConstans.BEARER_PREPIX+refreshToken)
                        )
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                        .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                        .andExpect(jsonPath("$.message").value("헤더가 잘못 입력되었습니다."))
                        .andExpect(jsonPath("$.data").isEmpty())
                ;

                mockMvc.perform(post("/api/auth/refresh-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(ProductConstans.AUTHORIZATION_HEADER, "test")
                        )
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                        .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                        .andExpect(jsonPath("$.message").value("헤더가 올바르지 않습니다."))
                        .andExpect(jsonPath("$.data").isEmpty())
                ;

            }


        }

        @Nested
        @DisplayName("로그아웃")
        class logout{

            @Test
            @DisplayName("로그아웃 컨트롤러 성공 테스트")
            void success() throws Exception {

                mockMvc.perform(post("/api/auth/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(ProductConstans.AUTHORIZATION_HEADER, ProductConstans.BEARER_PREPIX+accessToken)
                        )
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()))
                        .andExpect(jsonPath("$.data").isEmpty())
                ;

                mockMvc.perform(post("/api/auth/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(ProductConstans.AUTHORIZATION_HEADER, ProductConstans.BEARER_PREPIX+accessToken)
                        )
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                        .andExpect(jsonPath("$.code").value(HttpStatus.UNAUTHORIZED.value()))
                        .andExpect(jsonPath("$.message").value(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
                        .andExpect(jsonPath("$.data").isEmpty())
                ;
            }
            @Test
            @DisplayName("로그아웃 컨트롤러 실패 테스트")
            void fail() throws Exception {

                mockMvc.perform(post("/api/auth/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(ProductConstans.AUTHORIZATION_HEADER, ProductConstans.BEARER_PREPIX+refreshToken)
                        )
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                        .andExpect(jsonPath("$.code").value(HttpStatus.UNAUTHORIZED.value()))
                        .andExpect(jsonPath("$.message").value(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
                        .andExpect(jsonPath("$.data").isEmpty())
                ;

                mockMvc.perform(post("/api/auth/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("test", ProductConstans.BEARER_PREPIX+accessToken)
                        )
                        .andDo(print())
                        .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                        .andExpect(jsonPath("$.code").value(HttpStatus.UNAUTHORIZED.value()))
                        .andExpect(jsonPath("$.message").value(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
                        .andExpect(jsonPath("$.data").isEmpty())
                ;

            }


        }
    }


}