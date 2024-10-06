package com.product.user.request;

import com.product.user.dto.UserUpdateDTO;
import com.product.user.enums.EnumUserType;
import com.product.util.PatternUtil;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest
(

    @Size(max=100)
    String userName,

    EnumUserType userType,

    @Pattern(regexp = PatternUtil.EMAIL_REGEX, message = "이메일 양식이 맞지 않습니다.")
    String email,

    @Pattern(regexp = PatternUtil.PHONE_NUMBER_REGEX, message="휴대폰번호 양식이 맞지 않습니다.")
    String mobile,

    String description
)
{
    public UserUpdateDTO toDTO(Long userId){
        return UserUpdateDTO.builder()
                .userId(userId)
                .userName(this.userName)
                .userType(this.userType)
                .email(this.email)
                .mobile(this.mobile)
                .description(this.description)
                .build();
    }

}
