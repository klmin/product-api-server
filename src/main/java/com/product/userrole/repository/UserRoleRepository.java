package com.product.userrole.repository;

import com.product.jpa.repository.GenericJpaRepository;
import com.product.userrole.compositekey.UserRoleKey;
import com.product.userrole.entity.UserRole;


import java.util.Collection;
import java.util.Optional;

public interface UserRoleRepository extends GenericJpaRepository<UserRole, UserRoleKey> {
    <T> Optional<T> findByUserIdAndRoleId(Long userId, String roleId, Class<T> type);

    <T> Collection<T> findByUserId(Long userId, Class<T> type);

}
