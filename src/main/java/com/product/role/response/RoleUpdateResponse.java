package com.product.role.response;

import java.time.LocalDateTime;

public record RoleUpdateResponse
(
    String roleId,
    String roleName,
    String description,
    LocalDateTime updDttm
)
{
}
