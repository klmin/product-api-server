package com.product.userrole.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRoleCreateDto {
    private Long userId;
    private String roleId;
}
