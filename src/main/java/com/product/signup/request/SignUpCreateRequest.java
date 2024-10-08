package com.product.signup.request;

import com.product.signup.dto.SignUpCreateDto;
import com.product.util.PatternUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpCreateRequest(

        @NotBlank
        @Pattern(regexp = PatternUtil.PHONE_NUMBER_REGEX, message="휴대폰번호 양식이 맞지 않습니다.")
        String loginId,

        @NotBlank
        @Size(min=3, max=50)
        String userName,

        @NotBlank
        @Pattern(regexp = PatternUtil.PASSWORD_REGEX, message="비밀번호는 8자 이상 20자 이하, 하나의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.")
        String password,

        @Pattern(regexp = PatternUtil.EMAIL_REGEX, message = "이메일 양식이 맞지 않습니다.")
        String email,

        String description
) {
    public SignUpCreateDto toDTO() {
        return SignUpCreateDto.builder()
                .loginId(this.loginId)
                .userName(this.userName)
                .password(this.password)
                .email(this.email)
                .mobile(this.loginId)
                .description(this.description)
                .build();
    }
}
