package com.product.userrole.service;

import com.product.userrole.compositekey.UserRoleKey;
import com.product.userrole.dto.UserRoleCreateDto;
import com.product.userrole.entity.UserRole;


import java.util.Collection;

public interface UserRoleService {

    UserRole create(UserRoleCreateDto dto);

    void delete(UserRoleKey id);

    <T> T findByUserIdAndRoleId(Long userId, String roleId, Class<T> type);

    <T> Collection<T> loadRoleByUserId(Long userId, Class<T> type);



}
