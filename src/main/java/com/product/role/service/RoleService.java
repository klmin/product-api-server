package com.product.role.service;


import com.product.role.entity.Role;

public interface RoleService {

    Role get(String id);

    <T> T findByRoleId(String id, Class<T> type);

}
