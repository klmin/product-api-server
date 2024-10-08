package com.product.user.response;

import com.product.user.enums.EnumUserStatus;
import com.product.user.enums.EnumUserType;

import java.time.LocalDateTime;

public record UserDetailResponse
(
        Long userId,
    String loginId,
    String userName,
    EnumUserType userType,
    EnumUserStatus status,
    String email,
    String mobile,
    String description,
    LocalDateTime regDttm
)
{
}
