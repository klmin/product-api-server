package com.product.userrole.request;

import com.product.userrole.dto.UserRoleCreateDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRoleCreateRequest
(
    @NotNull
    Long userId,
    @NotBlank
    @Size(max=30)
    String roleId
)
{
    public UserRoleCreateDto toDTO(){
        return UserRoleCreateDto.builder()
                .userId(this.userId)
                .roleId(this.roleId)
        .build();
    }
}
