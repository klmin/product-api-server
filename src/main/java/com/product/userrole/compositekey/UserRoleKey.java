package com.product.userrole.compositekey;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class UserRoleKey implements Serializable {

    private Long userId;
    private String roleId;

    @Builder
    public UserRoleKey(Long userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

}
