package com.product.signup.controller;

import com.product.config.abstracts.AbstractMvcTest;
import com.product.signup.request.SignUpCreateRequest;
import com.product.user.enums.EnumUserStatus;
import com.product.user.enums.EnumUserType;
import com.product.user.response.UserGetResponse;
import com.product.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class SignUpControllerTest extends AbstractMvcTest {

    @Autowired
    UserService userService;

    @Nested
    @DisplayName("회원가입 컨트롤러 테스트")
    class signUp{

        @DisplayName("회원가입 컨트롤러 성공 테스트")
        @ParameterizedTest
        @MethodSource
        void success (String loginId, String userName, String password, String email, String description, HttpStatus status) throws Exception {

            SignUpCreateRequest request = buildSignUpCreateRequest(loginId, userName, password, email, description);

            mockMvc.perform(post("/api/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.meta.code").value(status.value()))
                    .andExpect(jsonPath("$.meta.message").value(status.getReasonPhrase()))
            ;

            UserGetResponse response = userService.findByLoginId(loginId, UserGetResponse.class);

            assertEquals(response.loginId(), loginId);
            assertEquals(response.userName(), userName);
            assertEquals(EnumUserType.OWNER, response.userType());
            assertEquals(EnumUserStatus.ACTIVE, response.status());
            assertEquals(response.email(), email);
            assertEquals(response.mobile(), loginId);
            assertEquals(response.description(), description);

        }

        @DisplayName("회원가입 컨트롤러 실패 테스트")
        @ParameterizedTest
        @MethodSource
        void fail (String loginId, String userName, String password, String email, String description, HttpStatus status, String field) throws Exception {

            SignUpCreateRequest request = buildSignUpCreateRequest(loginId, userName, password, email, description);

            mockMvc.perform(post("/api/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andDo(print())
                    .andExpect(status().is(status.value()))
                    .andExpect(jsonPath("$.meta.code").value(status.value()))
                    .andExpect(jsonPath("$.data[0].field").value(field))
            ;

        }

        private static Stream<Arguments> success() {
            return Stream.of(
                    Arguments.of("01092348678", "홍길동1", "1234@aA!", "hong1@email.com", "테스트데이터", HttpStatus.CREATED),
                    Arguments.of("01042848678", "이순신1", "1234@aA!", "lee1@email.com", "", HttpStatus.CREATED),
                    Arguments.of("01056385679", "강감찬1", "1234@aA!", "kang1@email.com", "", HttpStatus.CREATED)
            );
        }

        private static Stream<Arguments> fail() {
            return Stream.of(
                    Arguments.of("0101234567", "홍길동", "1234@A!@#a", "test@email.com", "테스트데이터", HttpStatus.BAD_REQUEST, "loginId"),
                    Arguments.of("01012345678", "홍길동", "1234@A!@#", "test@email.com", "테스트데이터", HttpStatus.BAD_REQUEST, "password"),
                    Arguments.of("01012345678", "홍길동", "1234@!@#a", "test@email.com", "테스트데이터", HttpStatus.BAD_REQUEST, "password"),
                    Arguments.of("01012345678", "홍길동", "1234@A!@#a", "invalidEmail", "테스트데이터", HttpStatus.BAD_REQUEST, "email")
            );
        }

    }

    private SignUpCreateRequest buildSignUpCreateRequest(String loginId, String userName, String password, String email, String description) {
        return SignUpCreateRequest.builder()
                .loginId(loginId)
                .userName(userName)
                .password(password)
                .email(email)
                .description(description)
                .build();
    }
}