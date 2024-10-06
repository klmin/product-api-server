package com.product.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthGenerateTokenDTO {
    private String loginId;
    private String password;
}
