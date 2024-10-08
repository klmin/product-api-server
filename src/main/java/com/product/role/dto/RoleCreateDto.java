package com.product.role.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoleCreateDto {

    private String roleId;
    private String roleName;

}
