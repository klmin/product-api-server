package com.product.rolehierarchy.entity;



import com.product.jpa.audit.RegIdTime;
import com.product.role.entity.Role;
import com.product.rolehierarchy.compositekey.RoleHierarchyKey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_role_hierarchy")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(RoleHierarchyKey.class)
public class RoleHierarchy extends RegIdTime {

    @Id
    @Column(name="child_id")
    private String childId;

    @Id
    @Column(name="parent_id")
    private String parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", insertable = false, updatable = false)
    private Role childRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Role parentRole;


    @Builder
    public RoleHierarchy(String childId, String parentId){
        this.childId = childId;
        this.parentId = parentId;
    }

}
