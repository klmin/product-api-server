package com.product.signup.dto;

import com.product.user.dto.UserCreateDto;
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

    private Long companyId;

    private String email;

    private String mobile;

    private String description;
    @Builder.Default
    private List<String> roleIds = List.of("ROLE_OWNER");

    public UserCreateDto toUserCreateDTO() {
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
