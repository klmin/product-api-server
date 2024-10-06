package com.product.userrole.entity;


import com.product.jpa.audit.RegIdTime;
import com.product.role.entity.Role;
import com.product.user.entity.User;
import com.product.userrole.compositekey.UserRoleKey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_user_role")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(UserRoleKey.class)
public class UserRole extends RegIdTime {

    @Id
    @Column(name="user_id")
    private Long userId;

    @Id
    @Column(name="role_id")
    private String roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    @Builder
    public UserRole(Long userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
