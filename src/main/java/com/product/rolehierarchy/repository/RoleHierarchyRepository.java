package com.product.rolehierarchy.repository;

import com.product.jpa.repository.GenericJpaRepository;
import com.product.rolehierarchy.compositekey.RoleHierarchyKey;
import com.product.rolehierarchy.entity.RoleHierarchy;

import java.util.List;
import java.util.Optional;

public interface RoleHierarchyRepository extends GenericJpaRepository<RoleHierarchy, RoleHierarchyKey> {

    <T> Optional<T> findByChildIdAndParentId(String childId, String parentId, Class<T> type);
    <T> List<T> findAllBy(Class<T> type);

}
