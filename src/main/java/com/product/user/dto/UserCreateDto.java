package com.product.user.dto;


import com.product.user.enums.EnumUserStatus;
import com.product.user.enums.EnumUserType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserCreateDto {

    private String loginId;
    private String userName;
    private String password;
    private EnumUserStatus status;
    private EnumUserType userType;
    private String email;
    private String mobile;
    private String description;
    private List<String> roleIds;

}
