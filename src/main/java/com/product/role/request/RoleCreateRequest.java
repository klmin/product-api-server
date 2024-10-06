package com.product.role.request;

import com.product.role.dto.RoleCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RoleCreateRequest
(
    @NotBlank
    @Size(max=30)
    String roleId,

    @Size(max=100)
    String roleName
)
{
    public RoleCreateDTO toDTO(){
        return RoleCreateDTO.builder()
                .roleId(this.roleId)
                .roleName(this.roleName)
                .build();
    }
}
