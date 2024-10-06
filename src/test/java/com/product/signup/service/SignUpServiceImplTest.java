package com.product.signup.service;

import com.product.signup.dto.SignUpCreateDTO;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class SignUpServiceImplTest {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserService userService;

    @Nested
    @DisplayName("회원가입 서비스 테스트")
    class signup {

        @DisplayName("회원가입 서비스 성공 테스트")
        @ParameterizedTest
        @MethodSource
        void success (String loginId, String userName, String password, String email, String description) {

            SignUpCreateDTO request = buildSignUpCreateDTO(loginId, userName, password, email, description);

            signUpService.signUp(request);

            UserGetResponse response = userService.findByLoginId(loginId, UserGetResponse.class);

            assertEquals(response.loginId(), loginId);
            assertEquals(response.userName(), userName);
            assertEquals(EnumUserType.OWNER, response.userType());
            assertEquals(EnumUserStatus.ACTIVE, response.status());
            assertEquals(response.email(), email);
            assertEquals(response.mobile(), loginId);
            assertEquals(response.description(), description);

        }

        @DisplayName("회원가입 서비스 실패 테스트")
        @ParameterizedTest
        @MethodSource
        void fail(String loginId, String userName, String password, String email, String description, Class<? extends Throwable> clazz){

            SignUpCreateDTO request = buildSignUpCreateDTO(loginId, userName, password, email, description);
            assertThrows(clazz, () -> signUpService.signUp(request), "예외가 발생해야함.");
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
                    Arguments.of("0101234567823232323", "홍길동", "1234@A!@#a", "test@email.com", "테스트데이터", DataIntegrityViolationException.class),
                    Arguments.of("01012345678", null, "1234@A!@#a", "test3@email.com", "테스트데이터", JpaSystemException.class)
            );
        }
    }

    private SignUpCreateDTO buildSignUpCreateDTO(String loginId, String userName, String password, String email, String description) {
        return SignUpCreateDTO.builder()
                .loginId(loginId)
                .userName(userName)
                .password(password)
                .mobile(loginId)
                .email(email)
                .description(description)
                .build();
    }
}