package com.product.user.service;

import com.product.user.dto.UserCreateDTO;
import com.product.user.dto.UserUpdateDTO;
import com.product.user.entity.User;
import com.product.user.enums.EnumUserStatus;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {

    void create(UserCreateDTO dto);

    void update(UserUpdateDTO dto);

    User get(Long userId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(Long userId);

    void changePassword(Long userId, String newPassword, String oldPassword);

    void changeStatus(Long userId, EnumUserStatus status);

    <T> T findByUserId(Long userId, Class<T> type);

    <T> T loadUserByLoginId(String loginId, EnumUserStatus status, Class<T> type);

    <T> T findByLoginId(String loginId, Class<T> type);

}


