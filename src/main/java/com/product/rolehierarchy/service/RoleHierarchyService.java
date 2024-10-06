package com.product.rolehierarchy.service;


public interface RoleHierarchyService {

    <T> T findByChildIdAndParentId(String childId, String parentId, Class<T> type);

    String getRoleHierarchyString();

}
