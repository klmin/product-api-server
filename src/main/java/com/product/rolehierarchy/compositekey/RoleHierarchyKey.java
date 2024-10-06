package com.product.rolehierarchy.compositekey;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class RoleHierarchyKey implements Serializable {
    private String childId;
    private String parentId;

    @Builder
    public RoleHierarchyKey(String childId, String parentId) {
        this.childId = childId;
        this.parentId = parentId;
    }
}
