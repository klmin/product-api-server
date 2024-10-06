package com.product.role.entity;


import com.product.jpa.audit.AuditableEntity;
import com.product.userrole.entity.UserRole;
import com.product.rolehierarchy.entity.RoleHierarchy;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name="tb_role")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends AuditableEntity {

    @Id
    @Column(name="role_id")
    private String roleId;

    @Comment("롤명")
    private String roleName;

    @Comment("설명")
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "childRole", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<RoleHierarchy> childRoles;

    @OneToMany(mappedBy = "parentRole", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<RoleHierarchy> parentRoles;


    @Builder
    public Role(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

}
