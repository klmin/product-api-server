package com.product.user.dto;


import com.product.user.enums.EnumUserStatus;
import com.product.user.enums.EnumUserType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserCreateDTO {

    private String loginId;
    private String userName;
    private String password;
    private EnumUserStatus status;
    private EnumUserType userType;
    private Long companyId;
    private String email;
    private String mobile;
    private String description;
    private List<String> roleIds;

}
