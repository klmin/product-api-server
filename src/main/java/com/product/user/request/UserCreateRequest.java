package com.product.user.request;

import com.product.user.dto.UserCreateDto;
import com.product.user.enums.EnumUserStatus;
import com.product.user.enums.EnumUserType;
import com.product.util.PatternUtil;
import jakarta.validation.constraints.*;

import java.util.List;

public record UserCreateRequest
(
    @NotBlank
    @Pattern(regexp = PatternUtil.ID_REGEX, message="아이디 양식이 맞지 않습니다.")
    String loginId,

    @NotBlank
    @Size(min=3, max=50)
    String userName,

    @NotBlank
    @Pattern(regexp = PatternUtil.PASSWORD_REGEX, message="비밀번호는 8자 이상 20자 이하, 하나의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.")
    String password,

    @NotNull
    EnumUserStatus status,

    @NotNull
    EnumUserType userType,

    @NotBlank
    @Pattern(regexp = PatternUtil.EMAIL_REGEX, message = "이메일 양식이 맞지 않습니다.")
    String email,

    @NotBlank
    @Pattern(regexp = PatternUtil.PHONE_NUMBER_REGEX, message = "휴대폰번호 양식이 맞지 않습니다.")
    String mobile,

    @NotEmpty
    List<String> roleIds,

    String description
)
{
    public UserCreateDto toDTO(){
        return UserCreateDto.builder()
                .loginId(this.loginId)
                .userName(this.userName)
                .password(this.password)
                .status(this.status)
                .userType(this.userType)
                .email(this.email)
                .mobile(this.mobile)
                .roleIds(this.roleIds)
                .description(this.description)
                .build();
    }

}
