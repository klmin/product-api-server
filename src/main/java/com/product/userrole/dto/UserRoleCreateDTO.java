package com.product.userrole.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRoleCreateDTO {
    private Long userId;
    private String roleId;
}
