package com.product.role.request;

import com.product.role.dto.RoleUpdateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RoleUpdateRequest
(
    @NotBlank
    String roleId,

    @Size(max=100)
    String roleName,

    String description
)
{
    public RoleUpdateDTO toDTO(){
        return RoleUpdateDTO.builder()
                .roleId(this.roleId)
                .roleName(this.roleName)
                .description(this.description)
                .build();
    }
}
