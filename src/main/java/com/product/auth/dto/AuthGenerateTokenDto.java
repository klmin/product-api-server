package com.product.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthGenerateTokenDto {
    private String loginId;
    private String password;
}
