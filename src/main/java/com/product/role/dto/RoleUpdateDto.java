package com.product.role.dto;

import com.product.role.entity.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@Builder
public class RoleUpdateDto {

    private String roleId;
    private String roleName;
    private String description;

    public void update(Role role){
        if(StringUtils.hasText(this.roleName)){
            role.setRoleName(this.roleName);
        }
        if(StringUtils.hasText(this.description)){
            role.setRoleName(this.description);
        }
    }
}
