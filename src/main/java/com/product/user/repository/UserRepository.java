package com.product.user.repository;


import com.product.jpa.repository.GenericJpaRepository;
import com.product.user.entity.User;
import com.product.user.enums.EnumUserStatus;

import java.util.Optional;

public interface UserRepository extends GenericJpaRepository<User, Long> {

    <T> Optional<T> findByUserId(Long id, Class<T> type);

    <T> Optional<T> findByLoginId(String loginId, Class<T> type);

    <T> Optional<T> findByLoginIdAndStatus(String loginId, EnumUserStatus status, Class<T> type);


}
