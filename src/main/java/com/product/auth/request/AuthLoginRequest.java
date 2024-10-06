package com.product.auth.request;

import com.product.auth.dto.AuthGenerateTokenDTO;
import com.product.util.PatternUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Builder
public record AuthLoginRequest
(
    @NotBlank
    @Size(max=30)
    String loginId,
    @NotBlank
    @Pattern(regexp = PatternUtil.PASSWORD_REGEX, message="비밀번호는 8자 이상 20자 이하, 하나의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.")
    String password

)
{
    public AuthGenerateTokenDTO toTokenDTO(){
        return AuthGenerateTokenDTO.builder()
        .loginId(this.loginId)
        .password(this.password)
        .build();
    }

}
