package com.product.user.projection;


import com.product.user.enums.EnumUserStatus;

public record UserProjection
(
        Long userId,
    String loginId,
    String password,
    EnumUserStatus status
)
{
}
