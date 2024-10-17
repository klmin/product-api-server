package com.product.signup.dto;

import com.product.user.enums.EnumUserStatus;
import com.product.user.enums.EnumUserType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SignUpCreateDto {

    private String loginId;

    private String userName;

    private String password;

    @Builder.Default
    private EnumUserStatus status = EnumUserStatus.ACTIVE;

    @Builder.Default
    private EnumUserType userType = EnumUserType.OWNER;

    private String email;

    private String mobile;

    private String description;
    @Builder.Default
    private List<String> roleIds = List.of("ROLE_OWNER");

}
