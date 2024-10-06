package com.product.role.repository;



import com.product.jpa.repository.GenericJpaRepository;
import com.product.role.entity.Role;

import java.util.Optional;

public interface RoleRepository extends GenericJpaRepository<Role, String> {

    <T> Optional<T> findByRoleId(String id, Class<T> type);
}
